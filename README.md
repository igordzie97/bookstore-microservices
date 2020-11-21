# Book Store
Authors: Adrian Nędza, Igor Dzierwa, Konrad Makuch\

mvn clean package -DskipTests

#maingateway: localhost:9091
nowy gateway któy działa tak jak poprzedni z tym że w miarę dobrze


/accounts-service/registration
podajemy w formData
username, password  
jeszcze nie śmiga dobrze do końca bo można dodać 2x to samo username.

/accounts-service/auth
podajemy w formdata
username, password 
w odpowiedzi dostajemy token Bearer
który nalezy wykorzystywać do reqiestów
(postman authorization bearer token) 
są 4 role - admin, employee, user, notRegisteredUser - przyda się do składania zamówień jako "niezalogowany"

w bearerze jest zaszyta rola i na jej podstawie się regquest autoryzują, analogicznie jak w librario, dużo kodu i konfiguracji przydało się stamtąd też.

w maingateway jest konfiguracja autoryzacji (które role mają do czego dostęp SecutiryTokenConfig.java)

przykładowe:
 "/accounts-service/logged/test_tylko_admin - musi byc admin
  "/accounts-service/logged/**" - musi być user
   "/accounts-service/**"- niezalogowani

#SWAGGER
http://localhost:9091/swagger-ui.html - jeden endpoint dla wszystkich serwisów


# gatewayService - na razie nie używamy
ale zostawiam bo konfiguracja feign się może przydać
localhost:9090/proxy/account - wywołanie przykładowej metody z serwisu accounts-service\
localhost:9090/proxy/basket - wywołanie przykładowej metody z serwisu baksets-service\
localhost:9090/proxy/order - wywołanie przykładowej metody z serwisu orders-service\
localhost:9090/proxy/product - wywołanie przykładowej metody z serwisu products-service
