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

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.demo.MainLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * Demo view for HorizontalLayout component.
 */
@Route(value = "horizontal-layout", layout = MainLayout.class)
@PageTitle("Horizontal Layout | Vaadin Kitchen Sink")
public class HorizontalLayoutDemoView extends VerticalLayout {

    public HorizontalLayoutDemoView() {
        setSpacing(true);
        setPadding(true);
        setMaxWidth("900px");

        add(new H1("Horizontal Layout Component"));
        add(new Paragraph("HorizontalLayout arranges components in a horizontal row."));

        // Basic horizontal layout
        HorizontalLayout basic = new HorizontalLayout();
        basic.add(createBox("Item 1"), createBox("Item 2"), createBox("Item 3"));
        basic.addClassNames(LumoUtility.Border.ALL);
        basic.setWidthFull();
        addSection("Basic Horizontal Layout", basic);

        // With spacing
        HorizontalLayout withSpacing = new HorizontalLayout();
        withSpacing.setSpacing(true);
        withSpacing.add(createBox("Spaced 1"), createBox("Spaced 2"), createBox("Spaced 3"));
        withSpacing.addClassNames(LumoUtility.Border.ALL);
        withSpacing.setWidthFull();
        addSection("With Spacing", withSpacing);

        // With padding
        HorizontalLayout withPadding = new HorizontalLayout();
        withPadding.setPadding(true);
        setMaxWidth("900px");
        withPadding.setSpacing(true);
        withPadding.add(createBox("Padded 1"), createBox("Padded 2"), createBox("Padded 3"));
        withPadding.addClassNames(LumoUtility.Border.ALL);
        withPadding.setWidthFull();
        addSection("With Padding", withPadding);

        // Alignment start
        HorizontalLayout alignStart = new HorizontalLayout();
        alignStart.setAlignItems(FlexComponent.Alignment.START);
        alignStart.add(createBox("Top"), createTallBox("Tall"), createBox("Aligned"));
        alignStart.setHeight("150px");
        alignStart.addClassNames(LumoUtility.Border.ALL);
        alignStart.setWidthFull();
        addSection("Align Items: Start (Top)", alignStart);

        // Alignment center
        HorizontalLayout alignCenter = new HorizontalLayout();
        alignCenter.setAlignItems(FlexComponent.Alignment.CENTER);
        alignCenter.add(createBox("Center"), createTallBox("Tall"), createBox("Aligned"));
        alignCenter.setHeight("150px");
        alignCenter.addClassNames(LumoUtility.Border.ALL);
        alignCenter.setWidthFull();
        addSection("Align Items: Center", alignCenter);

        // Alignment end
        HorizontalLayout alignEnd = new HorizontalLayout();
        alignEnd.setAlignItems(FlexComponent.Alignment.END);
        alignEnd.add(createBox("Bottom"), createTallBox("Tall"), createBox("Aligned"));
        alignEnd.setHeight("150px");
        alignEnd.addClassNames(LumoUtility.Border.ALL);
        alignEnd.setWidthFull();
        addSection("Align Items: End (Bottom)", alignEnd);

        // Justify content end
        HorizontalLayout justifyEnd = new HorizontalLayout();
        justifyEnd.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        justifyEnd.add(createBox("End 1"), createBox("End 2"), createBox("End 3"));
        justifyEnd.addClassNames(LumoUtility.Border.ALL);
        justifyEnd.setWidthFull();
        addSection("Justify Content: End", justifyEnd);

        // Justify content between
        HorizontalLayout justifyBetween = new HorizontalLayout();
        justifyBetween.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        justifyBetween.add(createBox("Left"), createBox("Center"), createBox("Right"));
        justifyBetween.addClassNames(LumoUtility.Border.ALL);
        justifyBetween.setWidthFull();
        addSection("Justify Content: Space Between", justifyBetween);

        // Justify content around
        HorizontalLayout justifyAround = new HorizontalLayout();
        justifyAround.setJustifyContentMode(FlexComponent.JustifyContentMode.AROUND);
        justifyAround.add(createBox("Around 1"), createBox("Around 2"), createBox("Around 3"));
        justifyAround.addClassNames(LumoUtility.Border.ALL);
        justifyAround.setWidthFull();
        addSection("Justify Content: Space Around", justifyAround);

        // With expand
        HorizontalLayout withExpand = new HorizontalLayout();
        Div expandItem = createBox("Expanded");
        withExpand.setFlexGrow(1, expandItem);
        withExpand.add(createBox("Fixed"), expandItem, createBox("Fixed"));
        withExpand.addClassNames(LumoUtility.Border.ALL);
        withExpand.setWidthFull();
        addSection("With Expand", withExpand);

        // Wrapping
        HorizontalLayout wrapping = new HorizontalLayout();
        wrapping.getStyle().set("flex-wrap", "wrap");
        for (int i = 1; i <= 10; i++) {
            wrapping.add(createBox("Item " + i));
        }
        wrapping.addClassNames(LumoUtility.Border.ALL);
        wrapping.setWidthFull();
        addSection("Wrapping (flex-wrap)", wrapping);
    }

    private Div createBox(String text) {
        Div box = new Div();
        box.setText(text);
        box.addClassNames(LumoUtility.Background.CONTRAST_10, LumoUtility.Padding.MEDIUM,
                LumoUtility.BorderRadius.MEDIUM);
        return box;
    }

    private Div createTallBox(String text) {
        Div box = createBox(text);
        box.setHeight("80px");
        return box;
    }

    private void addSection(String title, com.vaadin.flow.component.Component... components) {
        Div section = new Div();
        section.addClassNames(LumoUtility.Margin.Top.MEDIUM);
        section.add(new H3(title));
        VerticalLayout layout = new VerticalLayout(components);
        layout.setSpacing(true);
        layout.setPadding(false);
        layout.setWidthFull();
        section.add(layout);
        add(section);
    }
}
