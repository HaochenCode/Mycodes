#include <stdio.h>
#define MAX 20

typedef struct product{
    char * name;
    float price;
} product;


typedef struct lineItem{
    product prod;
    int quantity;
} lineItem;


typedef struct address{
    char * streetAddress;
    char * city;
    char * state;
    char * zip;
} address;


typedef struct invoice{
    lineItem * items;
    address addr;
}invoice;


address createAddress(char * street, char * city, char * state, char * zip);
product createProducts(char * name, double price);
lineItem createLineItem(product prod, int quantity);
invoice createInvoice(lineItem * items, address address);
void printInvoice(invoice invoice);

int main(void){
    address a = createAddress("100 Main Street","Anytowm","CA","98765");
    product p1 = createProducts("Banana",2.0);
    product p2 = createProducts("Apple",3.0);
    product p3 = createProducts("Beef",30.0);
    lineItem i1 = createLineItem(p1,3);
    lineItem i2 = createLineItem(p2,5);
    lineItem i3 = createLineItem(p3,1);
    lineItem list[3];
    list[0] = i1;
    list[1] = i2;
    list[2] = i3;
    invoice inv = createInvoice(list,a);
    printInvoice(inv);

    return 0;
}

address createAddress(char * street, char * city, char * state, char * zip){
    address ad = {street,city,state,zip};
    return ad;
}

product createProducts(char * name, double price){
    product p = { .name = name, .price = price };
    return p;
}

lineItem createLineItem(product prod, int quantity){
    lineItem i = { .prod = prod, .quantity = quantity };
    return i;
}

invoice createInvoice(lineItem * items, address address){
    invoice in = { .items = items, .addr = address };
    return in;
}

void printInvoice(invoice invoice){
    float sum = 0;
    float total = 0;
    printf("\t\tINVOICE\n\n");
    printf("Sam's Small Appliances\n");
    printf("%s\n",invoice.addr.streetAddress);
    printf("%s, %s  %s\n\n",invoice.addr.city,invoice.addr.state,invoice.addr.zip);
    printf("Description:          Price Qty Total\n");
    for(int i = 0 ;i < 3;i++){
        sum = invoice.items[i].quantity * invoice.items[i].prod.price;
        total += sum;
        printf("%-22s%.2f  %d  %.2f\n",invoice.items[i].prod.name, invoice.items[i].prod.price, invoice.items[i].quantity,sum);
    }
    printf("\n");
    printf("AMOUNT DUE:  $%.2f\n",total);
}