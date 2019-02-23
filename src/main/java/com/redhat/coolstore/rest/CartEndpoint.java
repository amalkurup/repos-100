	package com.redhat.coolstore.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import com.redhat.coolstore.model.kie.Product;
import com.redhat.coolstore.model.kie.ShoppingCart;
import com.redhat.coolstore.model.kie.ShoppingCartItem;
import com.redhat.coolstore.service.ShoppingCartService;


@RestController
@RequestMapping("/cart")
public class CartEndpoint implements Serializable {
	private static final Logger LOG = LoggerFactory.getLogger(CartEndpoint.class);

    /**
     *
     */
    private static final long serialVersionUID = -7227732980791688773L;

    @Autowired
    private ShoppingCartService shoppingCartService;

    
    @RequestMapping(value = "/{cartId}", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public ShoppingCart getCart(@PathParam("cartId") String cartId) {

        return shoppingCartService.getShoppingCart(cartId);
    }


    @RequestMapping(value="/{cartId}/{itemId}/{quantity}", method= RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public ShoppingCart add(@PathParam("cartId") String cartId,
                            @PathParam("itemId") String itemId,
                            @PathParam("quantity") int quantity) throws Exception {
        return shoppingCartService.addItem(cartId, itemId, quantity);
    }

    @RequestMapping(value = "/{cartId}/{tmpId}", method= RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public ShoppingCart set(@PathParam("cartId") String cartId,
                            @PathParam("tmpId") String tmpId) throws Exception {

        return shoppingCartService.set(cartId, tmpId);
    }

	

	  
	@RequestMapping(value = "/{cartId}/{itemId}/{quantity}", method = RequestMethod.DELETE)
	@Produces(MediaType.APPLICATION_JSON)
	public ShoppingCart delete(@PathParam("cartId") String cartId, @PathParam("itemId") String itemId,
			@PathParam("quantity") int quantity) throws Exception {

		return shoppingCartService.deleteItem(cartId, itemId, quantity);
	}

   
    @RequestMapping(value = "/checkout/{cartId}", method= RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public ShoppingCart checkout(@PathParam("cartId") String cartId) {
        // TODO: register purchase of shoppingCart items by specific user in session
        return shoppingCartService.checkout(cartId);
    }
}