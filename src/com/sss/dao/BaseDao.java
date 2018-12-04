package com.sss.dao;



public abstract class BaseDao  {



    protected abstract Object query(String sql);

    protected abstract boolean insert(String sql);

    protected abstract boolean update(String sql);

    protected abstract boolean delete(String sql);


}
