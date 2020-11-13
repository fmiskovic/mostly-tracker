package com.mostly.tracker.dto;

import java.io.Serializable;

public interface IdentifierDto<ID extends Serializable> {

    ID getIdentifier();

}
