package com.cg.capstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cg.capstore.dto.Cart;
import com.cg.capstore.service.CartService;



@Controller
public class CartController {

	@Autowired
	CartService service;

	public CartService getService() {
		return service;
	}

	public void setService(CartService service) {
		this.service = service;
	}

	@RequestMapping("getcartlist.obj")
	public ModelAndView showCartList(Model model){

		//To fetch data from database
		List<Cart> list = service.getAll();
		return new ModelAndView("home","list",list);	
	}

	@RequestMapping("add.obj")
	public String addToCart(@ModelAttribute("my") Cart cart){
		return "addform";
	}

	@RequestMapping("added.obj")
	public String addedToCart(@ModelAttribute("my") Cart cart){
		Cart c= service.add(cart);
		System.out.println(c);
		return "addsuccess";
	}

	@RequestMapping("delete.obj")
	public String deleteCart(@ModelAttribute("my") Cart cart){
		return "deleteform";
	}
	@RequestMapping("deleted.obj")
	public String deleteFromCart(@ModelAttribute("my") Cart cart){
		System.out.println(cart.getUser_Id());
		service.delete(cart.getUser_Id());
		return "deletesuccess";
	}

	@RequestMapping("increment")
	public String incremen(@RequestParam("cartid") String user_Id,Model model){

		Cart cart = service.getCartDetails(user_Id);
		System.out.println(cart);
		int quan=cart.getQuantity();
		int price=cart.getPrice();
		int newPrice = price/quan;
		quan=quan+1;
		price=newPrice*quan;
		System.out.println(quan);
		System.out.println(price);
		cart.setQuantity(quan);
		cart.setPrice(price);
		service.update(cart);
		List<Cart> list = service.getAll();
		System.out.println(list);
		model.addAttribute("list", list);
		return "home";
	}
	@RequestMapping("decrement")
	public String decrement(@RequestParam("cartid") String user_Id,Model model){

		Cart cart = service.getCartDetails(user_Id);
		System.out.println(cart);
		int quan=cart.getQuantity();
		if(quan>1)
		{

			int price=cart.getPrice();
			int newPrice = price/quan;
			quan=quan-1;
			price=newPrice*quan;
			System.out.println(quan);
			System.out.println(price);
			cart.setQuantity(quan);
			cart.setPrice(price);
			service.update(cart);
			List<Cart> list = service.getAll();
			System.out.println(list);
			model.addAttribute("list", list);
		}
		else
			service.delete(user_Id);
		List<Cart> list = service.getAll();
		System.out.println(list);
		model.addAttribute("list", list);
		return "home";
	}
}
