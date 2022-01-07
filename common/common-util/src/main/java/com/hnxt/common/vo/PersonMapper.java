package com.hnxt.common.vo;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Author yinz
 * @Date 2021/12/10 - 16:28
 */
@Mapper/*(componentModel = "spring")*/
public interface PersonMapper {

	PersonMapper instance = Mappers.getMapper(PersonMapper.class);

	@Mappings({
			@Mapping(target = "myId", source = "id"),
			@Mapping(target = "myName", source = "name")
	})
	PersonDto personToDto(Person p);

	/**
	 * 通过注解@InheritInverseConfiguration，实现与上述方法相反的对象转换
	 * @param p
	 * @return
	 */
	@InheritInverseConfiguration
	Person dtoToPerson(PersonDto p);
}
