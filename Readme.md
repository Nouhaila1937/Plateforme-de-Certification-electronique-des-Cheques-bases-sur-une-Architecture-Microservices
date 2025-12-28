# pour le frontend on essaie de le créer via la commande :

### Angular CLI
Installation (si pas encore) :
```bash
npm install -g @angular/cli
```
### Créer le projet Angular
```bash
ng new frontweb-app
```

### Lancer le serveur Angular
```bash 
cd frontweb-app
```
```bash 
ng serve --open
```
## Etape 1 création de tout les services dès le départ

## Etape 2 Développer et tester les micro-services , Discovery-service , Gateway-service et config-service

pour config-service on ajoute la notation de @EnableConfigServer bien sur on modifie le pom on modifie le application properties aussi 
pour discovery-service on met @EnableEurekaServer
pour gateway-service on pose dans la classe GatewayServiceApp
```bash
@Bean
DiscoveryClientRouteDefinitionLocator locator(
ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties dlp){
return new DiscoveryClientRouteDefinitionLocator(rdc,dlp);
}
```




















