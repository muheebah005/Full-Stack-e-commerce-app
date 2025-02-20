import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios';
import React, { createContext, useEffect, useState } from 'react';

export const CartContext = createContext();

export const CartProvider = ({ children }) => {
   const [cart, setCart] = useState([]);
   const [totalAmount, setTotalAmount] = useState(0);
   const [cartId, setCartId] = useState(null);

   useEffect(() => {
      const loadCartId = async () => {
         const storedCartId = await AsyncStorage.getItem('cartId');
         if (storedCartId) {
            setCartId(storedCartId);
            fetchCart(storedCartId);
         }
      };
      loadCartId();
   }, []);

   const fetchCart = async (cartId) => {
      try {
         const token = await AsyncStorage.getItem('userToken');
         if (!token) {
            throw new Error('User is not authenticated');
         }
         const authHeader = token.startsWith('Bearer ') ? token : `Bearer ${token}`;

         const response = await axios.get(
            `http://172.20.10.3:8080/api/v1/carts/${Number(cartId)}/mycart`,
            { headers: { Authorization: authHeader } }
         );

         if (response.data.success) {
            setCart(response.data.data.items);
            setTotalAmount(response.data.data.totalAmount);
         }
      } catch (error) {
         console.error('Error fetching cart:', error);
      }
   };

   const addToCart = async (product, quantity = 1) => {
      try {
         let token = await AsyncStorage.getItem('userToken');
         if (!token) {
            throw new Error('User is not authenticated');
         }
         const authHeader = token.startsWith('Bearer ') ? token : `Bearer ${token}`;
         const response = await axios.post(
            `http://172.20.10.3:8080/api/v1/cartItems/item/add`,
            null,
            {
               params: { productId: product.id, quantity },
               headers: { Authorization: authHeader },
            }
         );

         if (response.data.success) {
            const returnedCart = response.data.data;

            if (!cartId || Number(cartId) !== returnedCart.cartId) {
               const newCartId = returnedCart.cartId;

               setCartId(newCartId);
               await AsyncStorage.setItem('cartId', newCartId.toString());
               fetchCart(newCartId);
            } else {
               fetchCart(cartId);
            }
         }
      } catch (error) {
         console.error('Error adding to cart:', error);
      }
   };

   const updateCartItem = async (itemId, newQuantity) => {
      if (!cartId) return;
      try {
         const token = await AsyncStorage.getItem('userToken');
         const authHeader = token.startsWith('Bearer ') ? token : `Bearer ${token}`;
         await axios.put(
            `http://172.20.10.3:8080/api/v1/cartItems/cart/${cartId}/item/${itemId}/update`,
            null,
            { params: { quantity: newQuantity }, headers: { Authorization: authHeader } }
         );
         fetchCart(cartId);
      } catch (error) {
         console.error('Error updating cart item:', error);
      }
   };

   const removeFromCart = async (itemId) => {
      if (!cartId) return;
      try {
         const token = await AsyncStorage.getItem('userToken');
         const authHeader = token.startsWith('Bearer ') ? token : `Bearer ${token}`;
         await axios.delete(
            `http://172.20.10.3:8080/api/v1/cartItems/${cartId}/item/${itemId}/delete`,
            { headers: { Authorization: authHeader } }
         );
         fetchCart(cartId);
      } catch (error) {
         console.error('Error removing item from cart:', error);
      }
   };

   return (
      <CartContext.Provider
         value={{
            cart,
            totalAmount,
            addToCart,
            updateCartItem,
            removeFromCart,
            fetchCart,
            cartId,
         }}>
         {children}
      </CartContext.Provider>
   );
};
