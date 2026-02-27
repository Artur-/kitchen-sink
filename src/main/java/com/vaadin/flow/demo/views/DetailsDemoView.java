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

import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.demo.MainLayout;
import com.vaadin.flow.demo.Playground;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Demo view for Details component.
 */
@Route(value = "details", layout = MainLayout.class)
@PageTitle("Details | Vaadin Kitchen Sink")
public class DetailsDemoView extends VerticalLayout {

    public DetailsDemoView() {
        setSpacing(true);
        setPadding(true);
        setMaxWidth("900px");

        add(new H1("Details Component"));
        add(new Paragraph("Details provides an expandable/collapsible content section."));

        // Interactive playground
        Playground<Details> playground = new Playground<>(new Details("Click to expand",
                new Paragraph("This is the expandable content.")))
                .withCheckbox("Enabled", true, Details::setEnabled)
                .withCheckbox("Opened", false, Details::setOpened)
                .withCheckbox("Small", false, (d, val) -> {
                    if (val) {
                        d.addThemeVariants(DetailsVariant.SMALL);
                    } else {
                        d.removeThemeVariants(DetailsVariant.SMALL);
                    }
                })
                .withCheckbox("Reverse", false, (d, val) -> {
                    if (val) {
                        d.addThemeVariants(DetailsVariant.REVERSE);
                    } else {
                        d.removeThemeVariants(
                                DetailsVariant.REVERSE);
                    }
                })
                .withCheckbox("Filled", false, (d, val) -> {
                    if (val) {
                        d.addThemeVariants(DetailsVariant.FILLED);
                    } else {
                        d.removeThemeVariants(
                                DetailsVariant.FILLED);
                    }
                })
                .withTextField("Summary", "Click to expand",
                        Details::setSummaryText);

        // Basic details
        Details basic = new Details("Click to expand",
            new Paragraph("This is the hidden content that appears when expanded."));
        playground.addExample("Basic Details", basic);

        // Initially opened
        Details opened = new Details("Already Expanded",
            new Paragraph("This details component starts in an opened state."));
        opened.setOpened(true);
        playground.addExample("Initially Opened", opened);

        // With rich summary
        Span summary = new Span("Product Details");
        summary.getStyle().set("font-weight", "bold");
        Details richSummary = new Details(summary,
            new Paragraph("Product description, specifications, and other details."));
        playground.addExample("With Rich Summary", richSummary);

        // With rich content
        VerticalLayout content = new VerticalLayout();
        content.add(new Paragraph("Name: Premium Widget"));
        content.add(new Paragraph("Price: $29.99"));
        content.add(new Paragraph("Stock: In Stock"));
        content.add(new Paragraph("SKU: PWG-001"));
        content.setPadding(false);
        content.setSpacing(false);
        Details richContent = new Details("Product Information", content);
        playground.addExample("With Rich Content", richContent);

        // Small variant
        Details small = new Details("Small Variant",
            new Paragraph("This details uses the small theme variant."));
        small.addThemeVariants(DetailsVariant.SMALL);
        playground.addExample("Small Variant", small);

        // Reverse variant
        Details reverse = new Details("Toggle on Right",
            new Paragraph("The toggle icon is on the right side."));
        reverse.addThemeVariants(DetailsVariant.REVERSE);
        playground.addExample("Reverse Variant (Toggle on Right)", reverse);

        // Filled variant
        Details filled = new Details("Filled Background",
            new Paragraph("This details has a filled background when opened."));
        filled.addThemeVariants(DetailsVariant.FILLED);
        playground.addExample("Filled Variant", filled);

        // Combined variants
        Details combined = new Details("Combined Variants",
            new Paragraph("This combines small, reverse, and filled variants."));
        combined.addThemeVariants(DetailsVariant.SMALL, DetailsVariant.REVERSE, DetailsVariant.FILLED);
        playground.addExample("Combined Variants", combined);

        // With event listener
        Details withEvent = new Details("Click me",
            new Paragraph("Toggle state is tracked."));
        withEvent.addOpenedChangeListener(event ->
            Notification.show(event.isOpened() ? "Opened" : "Closed"));
        playground.addExample("With Event Listener", withEvent);

        // Disabled
        Details disabled = new Details("Disabled Details",
            new Paragraph("This content cannot be shown because the details is disabled."));
        disabled.setEnabled(false);
        playground.addExample("Disabled", disabled);

        // Multiple details (independent)
        VerticalLayout multipleContainer = new VerticalLayout();
        multipleContainer.setPadding(false);
        multipleContainer.add(
            new Details("Section 1", new Paragraph("Content for section 1")),
            new Details("Section 2", new Paragraph("Content for section 2")),
            new Details("Section 3", new Paragraph("Content for section 3"))
        );
        playground.addExample("Multiple Independent Details", multipleContainer);

        add(playground);
    }
}
