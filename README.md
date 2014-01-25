JEE
===

Pokročilá laboratoř Java EE

Maven
-----

Deploy na wildfly: `mvn wildfly:deploy`

Spuštění testů na wildfly: `mvn test -Parq-wildfly-remote`


Spusteni wildfly: ./standalone.sh -c standalone-full-ha.xml

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


