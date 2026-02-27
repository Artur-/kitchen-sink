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

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.demo.MainLayout;
import com.vaadin.flow.demo.Playground;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Demo view for Accordion component.
 */
@Route(value = "accordion", layout = MainLayout.class)
@PageTitle("Accordion | Vaadin Kitchen Sink")
public class AccordionDemoView extends VerticalLayout {

    public AccordionDemoView() {
        setSpacing(true);
        setPadding(true);
        setMaxWidth("900px");

        add(new H1("Accordion Component"));
        add(new Paragraph("Accordion organizes content into collapsible panels."));

        // Interactive playground
        Accordion playgroundAccordion = new Accordion();
        playgroundAccordion.add("Section 1",
                new Paragraph("Content of section 1"));
        playgroundAccordion.add("Section 2",
                new Paragraph("Content of section 2"));
        playgroundAccordion.add("Section 3",
                new Paragraph("Content of section 3"));
        playgroundAccordion.setWidthFull();
        Playground<Accordion> playground = new Playground<>(playgroundAccordion)
                .withCheckbox("Enabled", true,
                        (a, val) -> a.getChildren().forEach(
                                c -> ((AccordionPanel) c)
                                        .setEnabled(val)));

        // Basic accordion
        Accordion basic = new Accordion();
        basic.add("Personal Information",
            new Paragraph("Name, address, and contact details go here."));
        basic.add("Payment Details",
            new Paragraph("Credit card and billing information."));
        basic.add("Shipping Options",
            new Paragraph("Choose your preferred shipping method."));
        basic.setWidthFull();
        playground.addExample("Basic Accordion", basic);

        // Accordion with first panel open
        Accordion firstOpen = new Accordion();
        AccordionPanel panel1 = firstOpen.add("Introduction",
            new Paragraph("Welcome to our service! This section provides an overview."));
        firstOpen.add("Features",
            new Paragraph("Explore all the amazing features we offer."));
        firstOpen.add("Pricing",
            new Paragraph("View our competitive pricing plans."));
        firstOpen.open(panel1);
        firstOpen.setWidthFull();
        playground.addExample("First Panel Open by Default", firstOpen);

        // Accordion with rich content
        Accordion richContent = new Accordion();

        VerticalLayout personalInfo = new VerticalLayout();
        personalInfo.add(new Paragraph("First Name: John"));
        personalInfo.add(new Paragraph("Last Name: Doe"));
        personalInfo.add(new Paragraph("Email: john.doe@example.com"));
        personalInfo.setPadding(false);
        richContent.add("Contact Details", personalInfo);

        VerticalLayout orderInfo = new VerticalLayout();
        orderInfo.add(new Paragraph("Order #: 12345"));
        orderInfo.add(new Paragraph("Status: Processing"));
        orderInfo.add(new Paragraph("Total: $99.99"));
        orderInfo.setPadding(false);
        richContent.add("Order Information", orderInfo);

        richContent.setWidthFull();
        playground.addExample("Rich Content", richContent);

        // Accordion with disabled panel
        Accordion withDisabled = new Accordion();
        withDisabled.add("Available Section",
            new Paragraph("This section is available for interaction."));
        AccordionPanel disabledPanel = withDisabled.add("Unavailable Section",
            new Paragraph("This content is not accessible."));
        disabledPanel.setEnabled(false);
        withDisabled.add("Another Available Section",
            new Paragraph("This section is also available."));
        withDisabled.setWidthFull();
        playground.addExample("With Disabled Panel", withDisabled);

        // Accordion with event listener
        Accordion withEvents = new Accordion();
        withEvents.add("Section A", new Paragraph("Content of Section A"));
        withEvents.add("Section B", new Paragraph("Content of Section B"));
        withEvents.add("Section C", new Paragraph("Content of Section C"));
        Span statusLabel = new Span("No section opened");
        statusLabel.addClassNames(LumoUtility.TextColor.SECONDARY, LumoUtility.FontSize.SMALL);
        withEvents.addOpenedChangeListener(event -> {
            AccordionPanel openedPanel = event.getOpenedPanel().orElse(null);
            if (openedPanel != null) {
                statusLabel.setText("Currently open: " + openedPanel.getSummary().getElement().getText());
            } else {
                statusLabel.setText("All sections collapsed");
            }
        });
        withEvents.setWidthFull();
        playground.addExample("With Event Listener", withEvents, statusLabel);

        // FAQ example
        Accordion faq = new Accordion();
        faq.add("What payment methods do you accept?",
            new Paragraph("We accept Visa, MasterCard, American Express, and PayPal."));
        faq.add("How long does shipping take?",
            new Paragraph("Standard shipping takes 5-7 business days. Express shipping is 2-3 days."));
        faq.add("What is your return policy?",
            new Paragraph("You can return items within 30 days of purchase for a full refund."));
        faq.add("Do you ship internationally?",
            new Paragraph("Yes, we ship to over 50 countries worldwide."));
        faq.setWidthFull();
        playground.addExample("FAQ Example", faq);

        add(playground);
    }
}
