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
    })
});
