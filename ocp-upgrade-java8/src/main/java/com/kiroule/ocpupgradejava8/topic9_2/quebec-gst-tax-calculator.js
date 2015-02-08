/**
 * @author Igor Baiborodine
 */

function calculateGstTax(date, amount) {

    print("date[" + date + "], amount[" + amount + "]");
    var tax = 0.0;

    if (date >= "2008-01-01") {
        tax = amount * 0.05;
    } else if (date >= "2006-07-01") {
        tax = amount * 0.06;
    } else if (date >= "1991-01-01") {
        tax = amount * 0.07;
    }
    tax = tax.toFixed(2);
    print("tax[" + tax + "]");
    return tax.toString();
}
