package com.nsfl.tpetracker.html

import kotlinx.html.*
import kotlinx.html.attributes.enumEncode

@Suppress("unused")
open class INPUTGROUP(initialAttributes: Map<String, String>, override val consumer: TagConsumer<*>) :
        DIV(initialAttributes, consumer) {}

@Suppress("unused")
open class BOOTSTRAPROW(initialAttributes: Map<String, String>, override val consumer: TagConsumer<*>) :
        DIV(initialAttributes, consumer) {}

@HtmlTagMarker
fun FlowContent.inputGroup(classes: String? = null, block: INPUTGROUP.() -> Unit = {}): Unit =
        INPUTGROUP(attributesMapOf("class", "input-group ${classes ?: ""}"), consumer).visit(block)

@HtmlTagMarker
fun INPUTGROUP.prepend(classes: String? = null, block: DIV.() -> Unit = {}) {
    div(classes = "input-group-prepend ${classes ?: ""}", block = block)
}

@HtmlTagMarker
fun INPUTGROUP.append(classes: String? = null, block: DIV.() -> Unit = {}) {
    div(classes = "input-group-append ${classes ?: ""}", block = block)
}

@HtmlTagMarker
fun FlowContent.container(classes: String? = null, block: DIV.() -> Unit = {}) {
    div(classes = "container ${classes ?: ""}", block = block)
}

@HtmlTagMarker
fun FlowContent.row(classes: String? = null, block: BOOTSTRAPROW.() -> Unit = {}): Unit =
        BOOTSTRAPROW(attributesMapOf("class", "row ${classes ?: ""}"), consumer).visit(block)

@HtmlTagMarker
fun BOOTSTRAPROW.col(classes: String? = null, block: DIV.() -> Unit = {}) {
    div(classes = "col ${classes ?: ""}", block = block)
}

@HtmlTagMarker
fun BOOTSTRAPROW.colSm(classes: String? = null, block: DIV.() -> Unit = {}) {
    div(classes = "col-sm ${classes ?: ""}", block = block)
}

@HtmlTagMarker
fun FlowOrInteractiveOrPhrasingContent.btnPrimary(formEncType : ButtonFormEncType? = null, formMethod : ButtonFormMethod? = null, name : String? = null, type : ButtonType? = null, classes : String? = null, block : BUTTON.() -> Unit = {}) : Unit = BUTTON(attributesMapOf("class", "btn btn-primary ${classes ?: ""}", "formenctype", formEncType?.enumEncode(),"formmethod", formMethod?.enumEncode(),"name", name,"type", type?.enumEncode()), consumer).visit(block)
@HtmlTagMarker
fun FlowOrInteractiveOrPhrasingContent.btnSecondary(formEncType : ButtonFormEncType? = null, formMethod : ButtonFormMethod? = null, name : String? = null, type : ButtonType? = null, classes : String? = null, block : BUTTON.() -> Unit = {}) : Unit = BUTTON(attributesMapOf("class", "btn btn-secondary ${classes ?: ""}", "formenctype", formEncType?.enumEncode(),"formmethod", formMethod?.enumEncode(),"name", name,"type", type?.enumEncode()), consumer).visit(block)
@HtmlTagMarker
fun FlowOrInteractiveOrPhrasingContent.btnSuccess(formEncType : ButtonFormEncType? = null, formMethod : ButtonFormMethod? = null, name : String? = null, type : ButtonType? = null, classes : String? = null, block : BUTTON.() -> Unit = {}) : Unit = BUTTON(attributesMapOf("class", "btn btn-success ${classes ?: ""}", "formenctype", formEncType?.enumEncode(),"formmethod", formMethod?.enumEncode(),"name", name,"type", type?.enumEncode()), consumer).visit(block)
@HtmlTagMarker
fun FlowOrInteractiveOrPhrasingContent.btnDanger(formEncType : ButtonFormEncType? = null, formMethod : ButtonFormMethod? = null, name : String? = null, type : ButtonType? = null, classes : String? = null, block : BUTTON.() -> Unit = {}) : Unit = BUTTON(attributesMapOf("class", "btn btn-danger ${classes ?: ""}", "formenctype", formEncType?.enumEncode(),"formmethod", formMethod?.enumEncode(),"name", name,"type", type?.enumEncode()), consumer).visit(block)
@HtmlTagMarker
fun FlowOrInteractiveOrPhrasingContent.btnWarning(formEncType : ButtonFormEncType? = null, formMethod : ButtonFormMethod? = null, name : String? = null, type : ButtonType? = null, classes : String? = null, block : BUTTON.() -> Unit = {}) : Unit = BUTTON(attributesMapOf("class", "btn btn-warning ${classes ?: ""}", "formenctype", formEncType?.enumEncode(),"formmethod", formMethod?.enumEncode(),"name", name,"type", type?.enumEncode()), consumer).visit(block)
@HtmlTagMarker
fun FlowOrInteractiveOrPhrasingContent.btnInfo(formEncType : ButtonFormEncType? = null, formMethod : ButtonFormMethod? = null, name : String? = null, type : ButtonType? = null, classes : String? = null, block : BUTTON.() -> Unit = {}) : Unit = BUTTON(attributesMapOf("class", "btn btn-info ${classes ?: ""}", "formenctype", formEncType?.enumEncode(),"formmethod", formMethod?.enumEncode(),"name", name,"type", type?.enumEncode()), consumer).visit(block)
@HtmlTagMarker
fun FlowOrInteractiveOrPhrasingContent.btnLight(formEncType : ButtonFormEncType? = null, formMethod : ButtonFormMethod? = null, name : String? = null, type : ButtonType? = null, classes : String? = null, block : BUTTON.() -> Unit = {}) : Unit = BUTTON(attributesMapOf("class", "btn btn-light ${classes ?: ""}", "formenctype", formEncType?.enumEncode(),"formmethod", formMethod?.enumEncode(),"name", name,"type", type?.enumEncode()), consumer).visit(block)
@HtmlTagMarker
fun FlowOrInteractiveOrPhrasingContent.btnDark(formEncType : ButtonFormEncType? = null, formMethod : ButtonFormMethod? = null, name : String? = null, type : ButtonType? = null, classes : String? = null, block : BUTTON.() -> Unit = {}) : Unit = BUTTON(attributesMapOf("class", "btn btn-dark ${classes ?: ""}", "formenctype", formEncType?.enumEncode(),"formmethod", formMethod?.enumEncode(),"name", name,"type", type?.enumEncode()), consumer).visit(block)

@HtmlTagMarker fun FlowOrInteractiveOrPhrasingContent.aBtnPrimary(href : String? = null, target : String? = null, classes : String? = null, block : A.() -> Unit = {}) : Unit = A(attributesMapOf("class", "btn btn-primary ${classes ?: ""}","href", href,"target", target), consumer).visit(block)
@HtmlTagMarker fun FlowOrInteractiveOrPhrasingContent.aBtnSecondary(href : String? = null, target : String? = null, classes : String? = null, block : A.() -> Unit = {}) : Unit = A(attributesMapOf("class", "btn btn-secondary ${classes ?: ""}","href", href,"target", target), consumer).visit(block)
@HtmlTagMarker fun FlowOrInteractiveOrPhrasingContent.aBtnSuccess(href : String? = null, target : String? = null, classes : String? = null, block : A.() -> Unit = {}) : Unit = A(attributesMapOf("class", "btn btn-success ${classes ?: ""}","href", href,"target", target), consumer).visit(block)
@HtmlTagMarker fun FlowOrInteractiveOrPhrasingContent.aBtnDanger(href : String? = null, target : String? = null, classes : String? = null, block : A.() -> Unit = {}) : Unit = A(attributesMapOf("class", "btn btn-danger ${classes ?: ""}","href", href,"target", target), consumer).visit(block)
@HtmlTagMarker fun FlowOrInteractiveOrPhrasingContent.aBtnWarning(href : String? = null, target : String? = null, classes : String? = null, block : A.() -> Unit = {}) : Unit = A(attributesMapOf("class", "btn btn-warning ${classes ?: ""}","href", href,"target", target), consumer).visit(block)
@HtmlTagMarker fun FlowOrInteractiveOrPhrasingContent.aBtnInfo(href : String? = null, target : String? = null, classes : String? = null, block : A.() -> Unit = {}) : Unit = A(attributesMapOf("class", "btn btn-info ${classes ?: ""}","href", href,"target", target), consumer).visit(block)
@HtmlTagMarker fun FlowOrInteractiveOrPhrasingContent.aBtnLight(href : String? = null, target : String? = null, classes : String? = null, block : A.() -> Unit = {}) : Unit = A(attributesMapOf("class", "btn btn-light ${classes ?: ""}","href", href,"target", target), consumer).visit(block)
@HtmlTagMarker fun FlowOrInteractiveOrPhrasingContent.aBtnDark(href : String? = null, target : String? = null, classes : String? = null, block : A.() -> Unit = {}) : Unit = A(attributesMapOf("class", "btn btn-dark ${classes ?: ""}","href", href,"target", target), consumer).visit(block)

fun UL.navItem(href: String, active: Boolean = false, block: A.() -> Unit) {
    li(classes = if (active) "nav-item active" else "nav-item") {
        a(href = href, classes = "nav-link", block = block)
    }
}

fun UL.navItemDropdown(name: String, active: Boolean = false, block: DIV.() -> Unit) {
    li(classes = if (active) "nav-item dropdown active" else "nav-item dropdown") {
        a(classes = "nav-link dropdown-toggle", href="#") {
            attributes["data-toggle"] = "dropdown"
            +name
        }
        div(classes = "dropdown-menu", block = block)
    }
}

fun DIV.dropdownItem(href: String, active: Boolean = false, block: A.() -> Unit) {
    a(href = href, classes = if (active) "dropdown-item active" else "dropdown-item", block = block)
}

@Suppress("unused")
open class CARD(initialAttributes: Map<String, String>, override val consumer: TagConsumer<*>) :
        DIV(initialAttributes, consumer) {}

@Suppress("unused")
open class CARDBODY(initialAttributes: Map<String, String>, override val consumer: TagConsumer<*>) :
        DIV(initialAttributes, consumer) {}

@HtmlTagMarker
fun FlowContent.card(classes: String? = null, block: CARD.() -> Unit = {}): Unit =
        CARD(attributesMapOf("class", "card ${classes ?: ""}"), consumer).visit(block)

@HtmlTagMarker
fun FlowOrInteractiveOrPhrasingContent.cardImgTop(alt : String? = null, src : String? = null, classes : String? = null, block : IMG.() -> Unit = {}) : Unit
        = IMG(attributesMapOf("class", "card-img-top ${classes ?: ""}", "alt", alt,"src", src), consumer).visit(block)

@HtmlTagMarker
fun CARD.cardBody(classes: String? = null, block: CARDBODY.() -> Unit = {}): Unit =
        CARDBODY(attributesMapOf("class", "card-body ${classes ?: ""}"), consumer).visit(block)

@HtmlTagMarker
fun CARDBODY.cardTitle(classes: String? = null, block: H5.() -> Unit = {}) {
    h5(classes = "card-title ${classes ?: ""}", block = block)
}

@HtmlTagMarker
fun CARDBODY.cardText(classes: String? = null, block: P.() -> Unit = {}) {
    p(classes = "card-text ${classes ?: ""}", block = block)
}
