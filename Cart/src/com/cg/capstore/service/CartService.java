package com.cg.capstore.service;

import java.util.List;

import com.cg.capstore.dto.Cart;

public interface CartService {
	public List<Cart> getAll();
	public Cart getCartDetails(String user_Id);
	public List<Cart> delete(String user_Id);
	public Cart add(Cart cart);
	public String update(Cart cart);
}
