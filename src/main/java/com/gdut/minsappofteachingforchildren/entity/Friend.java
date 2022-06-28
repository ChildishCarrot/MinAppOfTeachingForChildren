package com.gdut.minsappofteachingforchildren.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 * 螺杆菌帅的一
 * @author zhoubin
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Friend implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId1;

    private String userId2;

    private String id;


}
