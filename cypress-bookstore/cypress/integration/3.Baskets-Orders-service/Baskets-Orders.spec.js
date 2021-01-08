describe('Test of the Basket-Orders-service', () => {    
    
    const baseURLBasket = Cypress.env('api_server') + Cypress.env('baskets_url');
    const baseURLOrder = Cypress.env('api_server') + Cypress.env('orders_url');

    it('Should properly GET first product on the list', () => {
        cy.request({
            method: 'GET',
            url: Cypress.env('api_server') + Cypress.env('products_url') + 'book'
        }).then((response) => {
            expect(response.status).equal(200);
            const allBooks = response.body;
            expect(allBooks).to.not.be.null;

            cy.setLocalStorage('productObject', JSON.stringify(allBooks[0]));
            cy.saveLocalStorage();
        });
    });
    
    it('Should properly GET empty basket and save cookie', () => {
        cy.restoreLocalStorage();

        cy.request({
            method: 'GET',
            url: baseURLBasket + 'cart',
        }).then((response) => {
            expect(response.status).equal(200);
            expect(response.body.cartTotal).equal(0);

            cy.getCookie('shoppingCartId').then((cookie) => {
                const cookieObject = {
                    name: 'shoppingCartId',
                    value: cookie.value
                };
                cy.setLocalStorage('cookieBasket', JSON.stringify(cookieObject));
                cy.saveLocalStorage();
            });
        });
    });

    it('Should properly add one product to basket - with given cookie,', () => {
        cy.restoreLocalStorage();

        cy.getLocalStorage('cookieBasket').then((cookieObject) => {
            const cookie = JSON.parse(cookieObject);
            cy.setCookie(cookie.name, cookie.value);

            cy.getLocalStorage('productObject').then((productObject) => {

                const product = JSON.parse(productObject);

                cy.request({
                    method: 'POST',
                    url: baseURLBasket + 'cart/' + product.id
                }).then((response) => {
                    expect(response.status).equal(200);
                    expect(response.body.cartTotal).equal(product.price);
                });
            });
        });
    });

    it('Should properly delete item from basket - with given cookie', () => {
        cy.restoreLocalStorage();
        cy.getLocalStorage('cookieBasket').then((cookieObject) => {
            const cookie = JSON.parse(cookieObject);
            cy.setCookie(cookie.name, cookie.value);

            cy.getLocalStorage('productObject').then((productObject) => {

                const product = JSON.parse(productObject);

                cy.request({
                    method: 'GET',
                    url: baseURLBasket + 'cart',
                }).then((response) => {
                    expect(response.status).equal(200);
                    expect(response.body).to.not.be.null;
                    const basketItemID = response.body.itemList[0].productId;

                    cy.request({
                        method: 'DELETE',
                        url: baseURLBasket + 'cart/' + basketItemID
                    }).then((response1) => {
                        expect(response1.status).equal(200);
                        expect(response1.body.cartTotal).equal(0);
                    });
                });
            });
        });
    });

    it('Should properly add item to basket and make an order - with given cookie', () => {
        cy.restoreLocalStorage();
        cy.getLocalStorage('cookieBasket').then((cookieObject) => {
            const cookie = JSON.parse(cookieObject);
            cy.setCookie(cookie.name, cookie.value);

            cy.getLocalStorage('productObject').then((productObject) => {
                const product = JSON.parse(productObject);

                cy.request({
                    method: 'POST',
                    url: baseURLBasket + 'cart/' + product.id
                }).then((response) => {
                    expect(response.status).equal(200);
                    expect(response.body.cartTotal).equal(product.price);
                });

                cy.request({
                    method: 'POST',
                    url: baseURLOrder + 'order'
                }).then((response) => {
                    expect(response.status).equal(200);
                    expect(response.body.cartTotal).equal(product.price);
                })
            });
        });
    });

    it('Should properly GET the order', () => {
        cy.restoreLocalStorage();
        cy.getLocalStorage('cookieBasket').then((cookieObject) => {
            const cookie = JSON.parse(cookieObject);
            cy.setCookie(cookie.name, cookie.value);

            cy.getLocalStorage('productObject').then((productObject) => {

                const product = JSON.parse(productObject);

                cy.request({
                    method: 'GET',
                    url: baseURLOrder + 'order'
                }).then((response) => {
                    expect(response.status).equal(200);
                    expect(response.body.cartTotal).equal(product.price);
                });
            });
        });
    });
});