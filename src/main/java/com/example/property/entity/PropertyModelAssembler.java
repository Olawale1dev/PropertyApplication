package com.example.property.entity;

import com.example.property.controller.PropertyController;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PropertyModelAssembler extends RepresentationModelAssemblerSupport<Property, PropertyModel> {

    public PropertyModelAssembler(){
        super(PropertyController.class, PropertyModel.class);
    }

    @Override
    public PropertyModel toModel(Property entity) {
    PropertyModel model = new PropertyModel();
        BeanUtils.copyProperties(entity, model);
        return model;
    }
}
