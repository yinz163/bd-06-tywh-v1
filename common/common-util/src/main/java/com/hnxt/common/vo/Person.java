package com.hnxt.common.vo;

import lombok.Data;

/**
 * @Author yinz
 * @Date 2021/12/10 - 16:27
 */
@Data
public class Person {

	private String id;

	private String name;

	public static void main(String[] args) {
		Person p = new Person();
		p.setId("123");
		p.setName("namess");
		PersonDto dto = PersonMapper.instance.personToDto(p);
		System.out.println(dto);
		System.out.println(PersonMapper.instance.dtoToPerson(dto));

	}
}
