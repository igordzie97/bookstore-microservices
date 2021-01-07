describe('Test of the Products-service', () => {
    const baseURL = Cypress.env('api_server') + Cypress.env('products_url');

    before(() => {
        cy.fixture('admin').then((admin) => {
            cy.login(admin.username, admin.password, 'admin').then((tokenResponse) => {
                cy.setLocalStorage('token', tokenResponse);
            })
        });
    });

    it('Should properly add author and save his id with given name', () => {
        cy.getLocalStorage('token').then((token) => {
            cy.fixture('author').then((author) => {
                const random = Math.floor(Math.random() * (author.length - 1));
                const randomAuthor = author[random];
                cy.addAuthor(token, randomAuthor);
                cy.request({
                    method: 'GET',
                    url: baseURL + 'author/name/' + randomAuthor.name
                }).then((response1) => {
                    expect(response1.status).equal(200);
                    expect(response1.body.name).equal(randomAuthor.name);
                    expect(response1.body.year).equal(randomAuthor.year);
                    expect(response1.body.description).equal(randomAuthor.description);
                    cy.setLocalStorage('authorID', response1.body.id);
                    cy.saveLocalStorage();
                });
            });
        });
    });

    it('Should properly add a book to saved author id', () => {
        cy.restoreLocalStorage();
        cy.getLocalStorage('authorID').then((authorID) => {
            cy.getLocalStorage('token').then((token) => {
                cy.fixture('book').then((book) => {
                    const random = Math.floor(Math.random() * (book.length - 1));
                    const randomBook = book[random];
                    cy.addBook(token, randomBook, authorID);
                });
            });
        });
    });

    it('Should properly GET all books', () => {
        cy.request({
            method: 'GET',
            url: baseURL + 'book'
        }).then((response) => {
            expect(response.status).equal(200);
            expect(response.body).to.not.be.null;
        })
    })
})