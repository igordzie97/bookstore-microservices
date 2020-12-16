describe('Test of the Accounts-service', () => {
    const baseURL = Cypress.env('api_server') + Cypress.env('accounts_url');
 
    it('Should properly add a new user', () => {
        cy.fixture('user').then((user) => {
            cy.request({
                method: 'POST',
                url: baseURL + 'registration',
                form: true,
                body: user
            }).then((response) => {
                expect(response.status).equal(200);
                expect(response.body).equal('user added');
            })
        });
    });

    it('Should properly log-in as recently added user', () => {
        cy.fixture('user').then((user) => {
            cy.login(user.username, user.password, 'user');
        })
    });

    it.only('Should properly log-in as administrator', () => {
        cy.fixture('admin').then((admin) => {
            cy.login(admin.username, admin.password, 'admin').then((token) => {
                cy.request({
                    method: 'GET',
                    url: baseURL + 'logged/test_tylko_admin',
                    headers: {
                        Authorization: token
                    }
                }).then((response) => {
                    expect(response.status).equal(200);
                    expect(response.body).equal('OK - dostep tylko dla zalogowanego');
                })
            })
        });
    });
})