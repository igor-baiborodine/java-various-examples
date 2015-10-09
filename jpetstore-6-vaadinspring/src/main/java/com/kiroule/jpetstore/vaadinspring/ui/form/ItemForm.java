package com.kiroule.jpetstore.vaadinspring.ui.form;

import static java.lang.String.format;

import com.kiroule.jpetstore.vaadinspring.domain.Item;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author Igor Baiborodine
 */
public class ItemForm extends AbstractForm<Item> {

  private Label image;
  private TextField itemDescription;
  private TextField id;
  private TextField productDescription;
  private TextField price;
  private TextField stock;
  private Button addToCart;

  public ItemForm(Item item) {

    initFields(item);
    setSizeUndefined();
  }

  private void initFields(Item item) {

    image = new Label(item.getProduct().getDescription(), ContentMode.HTML);
    id = new MTextField("ID", item.getItemId()).withReadOnly(true);
    String itemDescriptionValue = item.getAttribute1() + " " + item.getProduct().getName();
    itemDescription = new MTextField("Description", itemDescriptionValue).withReadOnly(true);
    productDescription = new MTextField("Product", item.getProduct().getName()).withReadOnly(true);

    String listPriceValue = NumberFormat.getCurrencyInstance(Locale.CANADA).format(item.getListPrice());
    price = new MTextField("Price", listPriceValue).withReadOnly(true);
    stock = new MTextField("Stock", String.valueOf(item.getQuantity())).withReadOnly(true);
    addToCart = new Button("Add to Cart",
        // TODO: implement me
        //click -> UIEventBus.post(new UINavigationEvent(uri)));
        click -> Notification.show(format("Adding item %s to shopping cart", click.getButton().getData()),
            Notification.Type.HUMANIZED_MESSAGE));
    addToCart.setData(item.getItemId());
  }

  @Override
  protected Component createContent() {

    return new MVerticalLayout(
        image,
        new MFormLayout(
            id,
            itemDescription,
            productDescription,
            price,
            stock
        ).withWidth(""),
        addToCart
    ).withWidth("");
  }
}
