package com.ai.model;

import dev.langchain4j.model.output.structured.Description;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Description("an address")
@Data
@AllArgsConstructor
@NoArgsConstructor
class Address {
	String street;
	Integer streetNumber;
	String city;
}
