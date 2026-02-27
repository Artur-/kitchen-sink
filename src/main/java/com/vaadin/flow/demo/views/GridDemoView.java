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

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.demo.MainLayout;
import com.vaadin.flow.demo.Playground;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Demo view for Grid component.
 */
@Route(value = "grid", layout = MainLayout.class)
@PageTitle("Grid | Vaadin Kitchen Sink")
public class GridDemoView extends VerticalLayout {

    public GridDemoView() {
        setSpacing(true);
        setPadding(true);
        setMaxWidth("900px");

        add(new H1("Grid Component"));
        add(new Paragraph("Grid displays tabular data with sorting, filtering, and selection capabilities."));

        // Interactive playground
        Grid<Person> playgroundGrid = new Grid<>(Person.class, false);
        playgroundGrid.addColumn(Person::getFirstName)
                .setHeader("First Name").setSortable(true);
        playgroundGrid.addColumn(Person::getLastName)
                .setHeader("Last Name").setSortable(true);
        playgroundGrid.addColumn(Person::getEmail)
                .setHeader("Email").setFlexGrow(1);
        playgroundGrid.addColumn(Person::getAge)
                .setHeader("Age").setSortable(true);
        playgroundGrid.setItems(getSampleData());
        playgroundGrid.setHeight("300px");
        playgroundGrid.setWidthFull();
        Playground<Grid<Person>> playground = new Playground<>(playgroundGrid)
                .withCheckbox("Row stripes", false, (g, val) -> {
                    if (val) {
                        g.addThemeVariants(
                                GridVariant.LUMO_ROW_STRIPES);
                    } else {
                        g.removeThemeVariants(
                                GridVariant.LUMO_ROW_STRIPES);
                    }
                })
                .withCheckbox("Compact", false, (g, val) -> {
                    if (val) {
                        g.addThemeVariants(GridVariant.LUMO_COMPACT);
                    } else {
                        g.removeThemeVariants(
                                GridVariant.LUMO_COMPACT);
                    }
                })
                .withCheckbox("No border", false, (g, val) -> {
                    if (val) {
                        g.addThemeVariants(
                                GridVariant.LUMO_NO_BORDER);
                    } else {
                        g.removeThemeVariants(
                                GridVariant.LUMO_NO_BORDER);
                    }
                })
                .withCheckbox("Column reordering", false,
                        Grid::setColumnReorderingAllowed)
                .withSelect("Selection mode", "None",
                        List.of("None", "Single", "Multi"),
                        (g, mode) -> {
                            switch (mode) {
                            case "None" -> g.setSelectionMode(
                                    Grid.SelectionMode.NONE);
                            case "Single" -> g.setSelectionMode(
                                    Grid.SelectionMode.SINGLE);
                            case "Multi" -> g.setSelectionMode(
                                    Grid.SelectionMode.MULTI);
                            }
                        });

        // Basic grid
        Grid<Person> basic = new Grid<>(Person.class, false);
        basic.addColumn(Person::getFirstName).setHeader("First Name");
        basic.addColumn(Person::getLastName).setHeader("Last Name");
        basic.addColumn(Person::getEmail).setHeader("Email").setFlexGrow(1);
        basic.setItems(getSampleData());
        basic.setHeight("300px");
        basic.setWidthFull();
        playground.addExample("Basic Grid", basic);

        // Sortable grid
        Grid<Person> sortable = new Grid<>(Person.class, false);
        sortable.addColumn(Person::getFirstName).setHeader("First Name").setSortable(true);
        sortable.addColumn(Person::getLastName).setHeader("Last Name").setSortable(true);
        sortable.addColumn(Person::getEmail).setHeader("Email").setSortable(true).setFlexGrow(1);
        sortable.addColumn(Person::getAge).setHeader("Age").setSortable(true);
        sortable.setItems(getSampleData());
        sortable.setHeight("300px");
        sortable.setWidthFull();
        playground.addExample("Sortable Columns", sortable);

        // With selection
        Grid<Person> selectable = new Grid<>(Person.class, false);
        selectable.addColumn(Person::getFirstName).setHeader("First Name");
        selectable.addColumn(Person::getLastName).setHeader("Last Name");
        selectable.addColumn(Person::getEmail).setHeader("Email").setFlexGrow(1);
        selectable.setItems(getSampleData());
        selectable.setSelectionMode(Grid.SelectionMode.SINGLE);
        selectable.addSelectionListener(event ->
            event.getFirstSelectedItem().ifPresent(p ->
                Notification.show("Selected: " + p.getFirstName() + " " + p.getLastName())));
        selectable.setHeight("300px");
        selectable.setWidthFull();
        playground.addExample("Single Selection", selectable);

        // Multi-select
        Grid<Person> multiSelect = new Grid<>(Person.class, false);
        multiSelect.addColumn(Person::getFirstName).setHeader("First Name");
        multiSelect.addColumn(Person::getLastName).setHeader("Last Name");
        multiSelect.addColumn(Person::getEmail).setHeader("Email").setFlexGrow(1);
        multiSelect.setItems(getSampleData());
        multiSelect.setSelectionMode(Grid.SelectionMode.MULTI);
        multiSelect.addSelectionListener(event ->
            Notification.show("Selected: " + event.getAllSelectedItems().size() + " items"));
        multiSelect.setHeight("300px");
        multiSelect.setWidthFull();
        playground.addExample("Multi Selection", multiSelect);

        // With row stripes
        Grid<Person> striped = new Grid<>(Person.class, false);
        striped.addColumn(Person::getFirstName).setHeader("First Name");
        striped.addColumn(Person::getLastName).setHeader("Last Name");
        striped.addColumn(Person::getEmail).setHeader("Email").setFlexGrow(1);
        striped.addColumn(Person::getAge).setHeader("Age");
        striped.setItems(getSampleData());
        striped.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        striped.setHeight("300px");
        striped.setWidthFull();
        playground.addExample("Row Stripes", striped);

        // Compact variant
        Grid<Person> compact = new Grid<>(Person.class, false);
        compact.addColumn(Person::getFirstName).setHeader("First Name");
        compact.addColumn(Person::getLastName).setHeader("Last Name");
        compact.addColumn(Person::getEmail).setHeader("Email").setFlexGrow(1);
        compact.setItems(getSampleData());
        compact.addThemeVariants(GridVariant.LUMO_COMPACT);
        compact.setHeight("300px");
        compact.setWidthFull();
        playground.addExample("Compact Variant", compact);

        // No border variant
        Grid<Person> noBorder = new Grid<>(Person.class, false);
        noBorder.addColumn(Person::getFirstName).setHeader("First Name");
        noBorder.addColumn(Person::getLastName).setHeader("Last Name");
        noBorder.addColumn(Person::getEmail).setHeader("Email").setFlexGrow(1);
        noBorder.setItems(getSampleData());
        noBorder.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        noBorder.setHeight("300px");
        noBorder.setWidthFull();
        playground.addExample("No Border Variant", noBorder);

        // With column resizing
        Grid<Person> resizable = new Grid<>(Person.class, false);
        resizable.addColumn(Person::getFirstName).setHeader("First Name").setResizable(true);
        resizable.addColumn(Person::getLastName).setHeader("Last Name").setResizable(true);
        resizable.addColumn(Person::getEmail).setHeader("Email").setResizable(true).setFlexGrow(1);
        resizable.addColumn(Person::getAge).setHeader("Age").setResizable(true);
        resizable.setItems(getSampleData());
        resizable.setColumnReorderingAllowed(true);
        resizable.setHeight("300px");
        resizable.setWidthFull();
        playground.addExample("Resizable and Reorderable Columns", resizable);

        // With component column
        Grid<Person> withActions = new Grid<>(Person.class, false);
        withActions.addColumn(Person::getFirstName).setHeader("First Name");
        withActions.addColumn(Person::getLastName).setHeader("Last Name");
        withActions.addColumn(Person::getEmail).setHeader("Email").setFlexGrow(1);
        withActions.addComponentColumn(person -> {
            Button editBtn = new Button("Edit", e ->
                Notification.show("Edit: " + person.getFirstName()));
            return editBtn;
        }).setHeader("Actions");
        withActions.setItems(getSampleData());
        withActions.setHeight("300px");
        withActions.setWidthFull();
        playground.addExample("With Component Column", withActions);

        // Frozen columns
        Grid<Person> frozen = new Grid<>(Person.class, false);
        frozen.addColumn(Person::getFirstName).setHeader("First Name").setFrozen(true);
        frozen.addColumn(Person::getLastName).setHeader("Last Name");
        frozen.addColumn(Person::getEmail).setHeader("Email");
        frozen.addColumn(Person::getAge).setHeader("Age");
        frozen.addColumn(Person::getCity).setHeader("City");
        frozen.addColumn(Person::getCountry).setHeader("Country");
        frozen.setItems(getSampleData());
        frozen.setHeight("300px");
        frozen.setWidthFull();
        playground.addExample("Frozen First Column", frozen);

        add(playground);
    }

    private List<Person> getSampleData() {
        List<Person> people = new ArrayList<>();
        people.add(new Person("John", "Doe", "john.doe@example.com", 32, "New York", "USA"));
        people.add(new Person("Jane", "Smith", "jane.smith@example.com", 28, "London", "UK"));
        people.add(new Person("Bob", "Johnson", "bob.johnson@example.com", 45, "Paris", "France"));
        people.add(new Person("Alice", "Williams", "alice.w@example.com", 35, "Berlin", "Germany"));
        people.add(new Person("Charlie", "Brown", "charlie.b@example.com", 29, "Tokyo", "Japan"));
        people.add(new Person("Diana", "Miller", "diana.m@example.com", 41, "Sydney", "Australia"));
        people.add(new Person("Edward", "Davis", "edward.d@example.com", 38, "Toronto", "Canada"));
        people.add(new Person("Fiona", "Garcia", "fiona.g@example.com", 26, "Madrid", "Spain"));
        return people;
    }

    public static class Person {
        private String firstName;
        private String lastName;
        private String email;
        private int age;
        private String city;
        private String country;

        public Person(String firstName, String lastName, String email, int age, String city, String country) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.age = age;
            this.city = city;
            this.country = country;
        }

        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
    }
}
