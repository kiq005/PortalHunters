package com.example.kaique.portalhunters.dao;

/**
 * Created by kaique on 12/07/16.
 */
/**
 * CRUD
 *
 * C - create
 * R - read
 * U - update
 * D - delete
 */
public interface DAO {

    public void create(Object o);
    public Object read(Object o);
    public void update(Object o);
    public void delete(Object o);

}
