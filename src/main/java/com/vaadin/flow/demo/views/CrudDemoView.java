/*
 * Copyright 2000-2026 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.demo.views;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.demo.MainLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Demo view for Crud component.
 */
@Route(value = "crud", layout = MainLayout.class)
@PageTitle("CRUD | Vaadin Kitchen Sink")
public class CrudDemoView extends VerticalLayout {

    public CrudDemoView() {
        setSpacing(true);
        setPadding(true);
        setMaxWidth("900px");

        add(new H1("CRUD Component"));
        add(new Paragraph("CRUD provides a complete Create, Read, Update, Delete interface."));

        // Basic CRUD
        Crud<Employee> crud = new Crud<>(Employee.class, createEditor());

        // Configure grid columns
        crud.getGrid().removeAllColumns();
        crud.getGrid().addColumn(Employee::getFirstName).setHeader("First Name");
        crud.getGrid().addColumn(Employee::getLastName).setHeader("Last Name");
        crud.getGrid().addColumn(Employee::getEmail).setHeader("Email").setFlexGrow(1);
        crud.getGrid().addColumn(Employee::getDepartment).setHeader("Department");

        // Set up data provider using AbstractBackEndDataProvider with CrudFilter
        EmployeeDataProvider dataProvider = new EmployeeDataProvider();

        crud.setDataProvider(dataProvider);

        // Add CRUD listeners
        crud.addSaveListener(event -> {
            dataProvider.persist(event.getItem());
            Notification.show("Saved: " + event.getItem().getFirstName() + " " + event.getItem().getLastName());
        });

        crud.addDeleteListener(event -> {
            dataProvider.delete(event.getItem());
            Notification.show("Deleted: " + event.getItem().getFirstName());
        });

        crud.setHeight("500px");
        crud.setWidthFull();
        addSection("Employee Management", crud);
    }

    private CrudEditor<Employee> createEditor() {
        TextField firstName = new TextField("First Name");
        TextField lastName = new TextField("Last Name");
        EmailField email = new EmailField("Email");
        TextField department = new TextField("Department");

        FormLayout form = new FormLayout(firstName, lastName, email, department);

        Binder<Employee> binder = new Binder<>(Employee.class);
        binder.forField(firstName).asRequired().bind(Employee::getFirstName, Employee::setFirstName);
        binder.forField(lastName).asRequired().bind(Employee::getLastName, Employee::setLastName);
        binder.forField(email).asRequired().bind(Employee::getEmail, Employee::setEmail);
        binder.forField(department).bind(Employee::getDepartment, Employee::setDepartment);

        return new BinderCrudEditor<>(binder, form);
    }

    private void addSection(String title, com.vaadin.flow.component.Component... components) {
        Div section = new Div();
        section.add(new H3(title));
        VerticalLayout layout = new VerticalLayout(components);
        layout.setSpacing(true);
        layout.setPadding(false);
        layout.setWidthFull();
        section.add(layout);
        add(section);
    }

    private static class EmployeeDataProvider
            extends AbstractBackEndDataProvider<Employee, CrudFilter> {

        private final List<Employee> database = new ArrayList<>();

        EmployeeDataProvider() {
            database.add(new Employee("John", "Doe", "john.doe@company.com", "Engineering"));
            database.add(new Employee("Jane", "Smith", "jane.smith@company.com", "Marketing"));
            database.add(new Employee("Bob", "Johnson", "bob.j@company.com", "Sales"));
            database.add(new Employee("Alice", "Williams", "alice.w@company.com", "HR"));
            database.add(new Employee("Charlie", "Brown", "charlie.b@company.com", "Engineering"));
        }

        @Override
        protected Stream<Employee> fetchFromBackEnd(Query<Employee, CrudFilter> query) {
            Stream<Employee> stream = database.stream();

            if (query.getFilter().isPresent()) {
                stream = stream
                        .filter(predicate(query.getFilter().get()))
                        .sorted(comparator(query.getFilter().get()));
            }

            return stream.skip(query.getOffset()).limit(query.getLimit());
        }

        @Override
        protected int sizeInBackEnd(Query<Employee, CrudFilter> query) {
            return (int) fetchFromBackEnd(query).count();
        }

        void persist(Employee item) {
            if (!database.contains(item)) {
                database.add(item);
            }
            refreshAll();
        }

        void delete(Employee item) {
            database.remove(item);
            refreshAll();
        }

        private static Predicate<Employee> predicate(CrudFilter filter) {
            return filter.getConstraints().entrySet().stream()
                    .map(constraint -> (Predicate<Employee>) employee -> {
                        try {
                            Object value = valueOf(constraint.getKey(), employee);
                            return value != null && value.toString().toLowerCase()
                                    .contains(constraint.getValue().toLowerCase());
                        } catch (Exception e) {
                            return false;
                        }
                    })
                    .reduce(Predicate::and)
                    .orElse(e -> true);
        }

        private static Comparator<Employee> comparator(CrudFilter filter) {
            return filter.getSortOrders().entrySet().stream()
                    .map(sortClause -> {
                        try {
                            @SuppressWarnings("unchecked")
                            Comparator<Employee> comparator = Comparator.comparing(
                                    employee -> (Comparable) valueOf(sortClause.getKey(), employee));

                            if (sortClause.getValue() == SortDirection.DESCENDING) {
                                comparator = comparator.reversed();
                            }
                            return comparator;
                        } catch (Exception ex) {
                            return (Comparator<Employee>) (o1, o2) -> 0;
                        }
                    })
                    .reduce(Comparator::thenComparing)
                    .orElse((o1, o2) -> 0);
        }

        private static Object valueOf(String fieldName, Employee employee) {
            try {
                Field field = Employee.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(employee);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static class Employee {
        private String firstName;
        private String lastName;
        private String email;
        private String department;

        public Employee() {}

        public Employee(String firstName, String lastName, String email, String department) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.department = department;
        }

        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }
    }
}
