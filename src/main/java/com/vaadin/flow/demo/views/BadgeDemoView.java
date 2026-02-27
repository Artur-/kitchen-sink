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

import com.vaadin.flow.component.badge.Badge;
import com.vaadin.flow.component.badge.BadgeVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.demo.MainLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Demo view for Badge component.
 */
@Route(value = "badge", layout = MainLayout.class)
@PageTitle("Badge | Vaadin Kitchen Sink")
public class BadgeDemoView extends VerticalLayout {

    public BadgeDemoView() {
        setSpacing(true);
        setPadding(true);
        setMaxWidth("900px");

        add(new H1("Badge Component"));
        add(new Paragraph("Badges are used to highlight status information or counts."));

        // Basic badges
        HorizontalLayout basic = new HorizontalLayout();
        basic.setSpacing(true);
        basic.getStyle().set("flex-wrap", "wrap");
        basic.add(
            new Badge("Default"),
            createBadge("Success", BadgeVariant.SUCCESS),
            createBadge("Error", BadgeVariant.ERROR),
            createBadge("Warning", BadgeVariant.WARNING),
            createBadge("Contrast", BadgeVariant.CONTRAST)
        );
        addSection("Basic Badges", basic);

        // Filled badges
        HorizontalLayout filled = new HorizontalLayout();
        filled.setSpacing(true);
        filled.getStyle().set("flex-wrap", "wrap");
        filled.add(
            createBadge("Filled", BadgeVariant.FILLED),
            createBadge("Filled Success", BadgeVariant.FILLED, BadgeVariant.SUCCESS),
            createBadge("Filled Error", BadgeVariant.FILLED, BadgeVariant.ERROR),
            createBadge("Filled Warning", BadgeVariant.FILLED, BadgeVariant.WARNING),
            createBadge("Filled Contrast", BadgeVariant.FILLED, BadgeVariant.CONTRAST)
        );
        addSection("Filled Badges", filled);

        // With icons
        HorizontalLayout withIcons = new HorizontalLayout();
        withIcons.setSpacing(true);
        withIcons.getStyle().set("flex-wrap", "wrap");
        Badge approved = new Badge("Approved", VaadinIcon.CHECK.create());
        approved.addThemeVariants(BadgeVariant.SUCCESS);
        Badge rejected = new Badge("Rejected", VaadinIcon.CLOSE.create());
        rejected.addThemeVariants(BadgeVariant.ERROR);
        Badge pending = new Badge("Pending", VaadinIcon.CLOCK.create());
        Badge info = new Badge("Info", VaadinIcon.INFO.create());
        info.addThemeVariants(BadgeVariant.CONTRAST);
        withIcons.add(approved, rejected, pending, info);
        addSection("Badges with Icons", withIcons);

        // Number badges
        HorizontalLayout numbers = new HorizontalLayout();
        numbers.setSpacing(true);
        numbers.getStyle().set("flex-wrap", "wrap");
        Badge num1 = new Badge("messages", 5);
        Badge num2 = new Badge("alerts", 12);
        num2.addThemeVariants(BadgeVariant.ERROR);
        Badge num3 = new Badge("updates", 99);
        num3.addThemeVariants(BadgeVariant.SUCCESS);
        numbers.add(num1, num2, num3);
        addSection("Number Badges", numbers);

        // Number-only badges
        HorizontalLayout numberOnly = new HorizontalLayout();
        numberOnly.setSpacing(true);
        numberOnly.getStyle().set("flex-wrap", "wrap");
        Badge no1 = new Badge("messages", 5);
        no1.addThemeVariants(BadgeVariant.NUMBER_ONLY);
        Badge no2 = new Badge("alerts", 12);
        no2.addThemeVariants(BadgeVariant.NUMBER_ONLY, BadgeVariant.ERROR);
        Badge no3 = new Badge("updates", 99);
        no3.addThemeVariants(BadgeVariant.NUMBER_ONLY, BadgeVariant.SUCCESS);
        numberOnly.add(no1, no2, no3);
        addSection("Number-Only Badges", numberOnly);

        // Dot badges
        HorizontalLayout dots = new HorizontalLayout();
        dots.setSpacing(true);
        dots.getStyle().set("flex-wrap", "wrap");
        dots.setAlignItems(Alignment.CENTER);
        Badge dot1 = new Badge();
        dot1.addThemeVariants(BadgeVariant.DOT);
        Badge dot2 = new Badge();
        dot2.addThemeVariants(BadgeVariant.DOT, BadgeVariant.SUCCESS);
        Badge dot3 = new Badge();
        dot3.addThemeVariants(BadgeVariant.DOT, BadgeVariant.ERROR);
        Badge dot4 = new Badge();
        dot4.addThemeVariants(BadgeVariant.DOT, BadgeVariant.WARNING);
        dots.add(dot1, dot2, dot3, dot4);
        addSection("Dot Badges", dots);

        // Status badges example
        VerticalLayout statusExamples = new VerticalLayout();
        statusExamples.setPadding(false);
        statusExamples.setSpacing(true);

        HorizontalLayout status1 = new HorizontalLayout();
        status1.setAlignItems(Alignment.CENTER);
        Badge shipped = new Badge("Shipped");
        shipped.addThemeVariants(BadgeVariant.SUCCESS);
        status1.add(new Span("Order #1234"), shipped);

        HorizontalLayout status2 = new HorizontalLayout();
        status2.setAlignItems(Alignment.CENTER);
        status2.add(new Span("Order #1235"), new Badge("Processing"));

        HorizontalLayout status3 = new HorizontalLayout();
        status3.setAlignItems(Alignment.CENTER);
        Badge cancelled = new Badge("Cancelled");
        cancelled.addThemeVariants(BadgeVariant.ERROR);
        status3.add(new Span("Order #1236"), cancelled);

        statusExamples.add(status1, status2, status3);
        addSection("Status Badge Examples", statusExamples);
    }

    private Badge createBadge(String text, BadgeVariant... variants) {
        Badge badge = new Badge(text);
        badge.addThemeVariants(variants);
        return badge;
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
}
