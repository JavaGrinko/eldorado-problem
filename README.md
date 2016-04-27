<customers>
		<customer>
			<id>233658</id>
			<name>»горь ¬ладимирович</name>
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