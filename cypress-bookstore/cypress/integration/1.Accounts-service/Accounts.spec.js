describe('Test of the Accounts-service', () => {
    const baseURL = Cypress.env('api_server') + Cypress.env('accounts_url');
 
    it('Should properly add a new user and log-in with recently added user', () => {
        cy.fixture('user').then((user) => {
            const random = Math.floor(Math.random() * (user.length - 1));
            const randomUserDate = user[random];
            cy.addUser(randomUserDate);
            cy.login(randomUserDate.username, randomUserDate.password, 'user');
        });
    });

    it('Should properly log-in as administrator', () => {
        cy.fixture('admin').then((admin) => {
            cy.login(admin.username, admin.password, 'admin').then((token) => {
                cy.checkAdminRole(token);
            })
        });
    });
})