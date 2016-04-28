## Тестовое задание Eldorado

**Расположение дистрибутива приложения:** build/distributions/eldorado-problem.tar

**Требования:** Java 8

Для старта приложения необходимо запустить bat или bash скрипт в папке bin архива
В приложении используется **Embedded Tomcat**, поэтому для работы с приложением необходимо только ввести адрес в адресной строке:
http://localhost:1777

**Тест:** src/test/java/javagrinko/EldoradoProblemApplicationTests.java
Содержит два теста: один проверяет работу статистики, а второй тестирует корректность работы многопоточного парсера xml

### Исходный документ
```xml
<customers>
	<customer>
		<id>233658</id>
		<name>Username</name>
		<orders>
			<order>
				<id>233658</id>
				<positions>
					<position>
						<id>233658</id>
						<price>30.0</price>
						<count>5</count>
					</position>
				</positions>
			</order>
		</orders>
	</customer>
</customers>
```
### XSD-схема исходного документа
```xml
<xs:schema attributeFormDefault="unqualified" elementFormDefault="qualified" xmlns:xs="http://www.w3.org/2001/XMLSchema">
  <xs:element name="customers">
    <xs:complexType>
      <xs:sequence>
        <xs:element name="customer">
          <xs:complexType>
            <xs:sequence>
              <xs:element type="xs:int" name="id"/>
              <xs:element type="xs:string" name="name"/>
              <xs:element name="orders">
                <xs:complexType>
                  <xs:sequence>
                    <xs:element name="order">
                      <xs:complexType>
                        <xs:sequence>
                          <xs:element type="xs:int" name="id"/>
                          <xs:element name="positions">
                            <xs:complexType>
                              <xs:sequence>
                                <xs:element name="position">
                                  <xs:complexType>
                                    <xs:sequence>
                                      <xs:element type="xs:int" name="id"/>
                                      <xs:element type="xs:float" name="price"/>
                                      <xs:element type="xs:byte" name="count"/>
                                    </xs:sequence>
                                  </xs:complexType>
                                </xs:element>
                              </xs:sequence>
                            </xs:complexType>
                          </xs:element>
                        </xs:sequence>
                      </xs:complexType>
                    </xs:element>
                  </xs:sequence>
                </xs:complexType>
              </xs:element>
            </xs:sequence>
          </xs:complexType>
        </xs:element>
      </xs:sequence>
    </xs:complexType>
  </xs:element>
</xs:schema>
```
