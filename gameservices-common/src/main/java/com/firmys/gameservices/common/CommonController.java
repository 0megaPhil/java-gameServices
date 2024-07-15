package com.firmys.gameservices.common;

public abstract class CommonController<D extends CommonEntity> {

  public abstract CommonService<D> service();

  public abstract Class<D> entityClass();
}
