JEE
===

Pokročilá laboratoř Java EE

Maven
-----

Deploy na wildfly: `mvn wildfly:deploy`

Spuštění testů na wildfly: `mvn test -Parq-wildfly-remote`


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
Do <subsystem xmlns="urn:jboss:domain:security:1.2">
            <security-domains>
            pridate:
```
				<security-domain name="semestralka" cache-type="default">
                    <authentication>
                        <login-module name="Database " code="Database" flag="required">
                            <module-option name="dsJndiName " value="java:jboss/jeelab/AppDS"/>
                            <module-option name="principalsQuery" value="SELECT PASSWORD FROM USERS WHERE EMAIL=?"/>
                            <module-option name="rolesQuery" value="SELECT NAME, 'Roles' FROM USERS INNER JOIN USERS_ROLES ON (USERS.ID = USERS_ID) LEFT JOIN ROLES ON (ROLES_ID = ROLES.ID) WHERE EMAIL=?"/>
                        </login-module>
                    </authentication>
                </security-domain>
```
do standalone-full-ha.xml ($JBOSS_HOME/standalone/configuration) zmenit

zmenit v standalone-full-ha.xml
```
<default-security-domain value="other"/> na <default-security-domain value="semestralka"/>
```
