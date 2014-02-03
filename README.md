JEE
===

Pokročilá laboratoř Java EE

Maven
-----

Deploy na wildfly: `mvn wildfly:deploy`

Spuštění testů na wildfly: `mvn test -Parq-wildfly-remote`


Domena: 
Je to trochu slozitejsi
 1. Nainstalovat mysql
 2. Vytvorit uzivatele jee s heslem jee a databazi jee navod tady: http://stackoverflow.com/questions/6445917/connect-failed-access-denied-for-user-rootlocalhost-using-password-yes nebo si to udelejte jak chcete jde to zmenit v domain.xml na roota pokud budete chtit
 3. Vytvorit modul v $JBOSS_HOME (proste tam kde mate wildfly) dela se to podle tohohle: https://zorq.net/b/2011/07/12/adding-a-mysql-datasource-to-jboss-as-7/ staci po Create a Driver Reference dal uz nemusite je to vse udelany v domain.xml
 4. Nakopirovat si domain-our.xml z root gitu do `$JBOSS_HOME/domain/configuration/`
 5. Spustit wildfly v domene `./bin/domain.sh -c domain-our.xml`
 6. Zmenit v persistence.xml value="`none`" na `drop-and-create`
 7. Deploydnout aplikaci `mvn clean install wildfly:deploy` z netbeanu mi to neslo muselo jsem to delat z konzole
 8. Ted jeden server spadne kvuli db
 9. Zmenit v persistence.xml value="`drop-and-create`" na `none` 
 10. Deploy `mvn clean install wildfly:deploy`
 11. Uz by vse melo jet

Pozn.: z domain se pouziva pouze profil full-ha takze mente veci jenom v nem v ostatnich to nema smysl



Spusteni wildfly: `./standalone.sh -c standalone-full-ha.xml`

Pro file upload je potreba WildFly 8 CR1 v beta je chyba. Upload nefunguje v chrome, ale v ostatnich prohlizecich ano.

Infinipan:
do standalone-full-ha.xml ($JBOSS_HOME/standalone/configuration) nebo v budoucnu do domain.xml je potreba do kategorie ```<subsystem xmlns="urn:jboss:domain:infinispan:2.0">``` pridat:
```
<cache-container name="video" default-cache="data">
    <transport lock-timeout="60000"/>
    <replicated-cache name="metadata" start="EAGER" batching="true" mode="SYNC">
        <eviction strategy="LRU" max-entries="2"/>
        <file-store passivation="false"/>
    </replicated-cache>
    <distributed-cache name="data" start="EAGER" batching="true" mode="SYNC">
        <eviction strategy="LRU" max-entries="2"/>
        <file-store passivation="false"/>
    </distributed-cache>
</cache-container>
```

do standalone-full-ha.xml ($JBOSS_HOME/standalone/configuration)
Do ```<subsystem xmlns="urn:jboss:domain:security:1.2">```
            <security-domains>
            pridate:
```
				<security-domain name="semestralka" cache-type="default">
                    <authentication>
                        <login-module code="org.jboss.security.auth.spi.DatabaseServerLoginModule" flag="required">
                            <module-option name="dsJndiName" value="java:jboss/jeelab/AppDS"/>
                            <module-option name="principalsQuery" value="SELECT PASSWORD FROM USERS WHERE EMAIL=?"/>
                            <module-option name="rolesQuery" value="SELECT NAME, 'Roles' FROM USERS INNER JOIN USERS_ROLES ON (USERS.ID = USERS_ID) LEFT JOIN ROLES ON (ROLES_ID = ROLES.ID) WHERE EMAIL=?"/>
                        </login-module>
                    </authentication>
                </security-domain>
```
do standalone-full-ha.xml ($JBOSS_HOME/standalone/configuration) zmenit

zmenit v standalone-full-ha.xml
```
<default-security-domain value="other"/> 
<default-missing-method-permissions-deny-access value="true"/>
na 
<default-security-domain value="semestralka"/>
<default-missing-method-permissions-deny-access value="false"/>

```
