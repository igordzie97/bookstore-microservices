import "cypress-localstorage-commands";

Cypress.Commands.add('login', (username, password, role) => {
    const baseURL = Cypress.env('api_server') + Cypress.env('accounts_url');
    cy.request({
        method: 'POST',
        url: baseURL + 'auth',
        form: true,
        body: {
            username: username,
            password: password
        }
    }).then((response) => {
        expect(response.status).equal(200);
        expect(response.body.roles).equal(`[${role}]`);
        expect(response.body.message).equal('Login OK');
        expect(response.body.token).to.be.not.null;
        return response.body.token;
    });
});

Cypress.Commands.add('addUser', (user) => {
    const baseURL = Cypress.env('api_server') + Cypress.env('accounts_url');
    cy.request({
        method: 'POST',
        url: baseURL + 'registration',
        form: true,
        body: user
    }).then((response) => {
        expect(response.status).equal(200);
        expect(response.body).equal('user added');
    });
});

Cypress.Commands.add('checkAdminRole', (token) => {
    const baseURL = Cypress.env('api_server') + Cypress.env('accounts_url');
    cy.request({
        method: 'GET',
        url: baseURL + 'logged/test_tylko_admin',
        headers: {
            Authorization: token
        }
    }).then((response) => {
        expect(response.status).equal(200);
        expect(response.body).equal('OK - dostep tylko dla zalogowanego');
    });
});

Cypress.Commands.add('addBook', (token, book, authorID) => {
    const baseURL = Cypress.env('api_server') + Cypress.env('products_url');
    cy.request({
        method: 'POST',
        url: baseURL + 'admin/book',
        headers: {
            Authorization: token,
        },
        form: true,
        body: {
            'author.id': authorID,
            'name': book.name,
            'length': book.length,
            'stock': book.stock,
            'description': book.description,
            'price': book.price,
            'category': book.category
        }
    }).then((response) => {
        expect(response.status).equal(200);
        expect(response.body.message).equal('Added book successfully!');
    });
});

Cypress.Commands.add('addAuthor', (token, author) => {
    const baseURL = Cypress.env('api_server') + Cypress.env('products_url');
    cy.request({
        method: 'POST',
        url: baseURL + 'admin/author',
        headers: {
            'Authorization': token,
        },
        form: true,
        body: {
            'name': author.name,
            'year': author.year,
            'description': author.description
        }
        
    }).then((response) => {
        expect(response.status).equal(200);
        expect(response.body.message).equal('Added author successfully!');
        
    });
});

