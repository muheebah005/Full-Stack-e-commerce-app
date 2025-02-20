import Ionicons from '@expo/vector-icons/Ionicons';
import { useNavigation } from '@react-navigation/native';
import React, { useContext, useEffect } from 'react';
import { FlatList, Image, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { CartContext } from '../ItemCard/CartContext';
import placeholderImage from '../assets/images/placeholderr.png';

const CartScreen = () => {
   const { cart, totalAmount, updateCartItem, removeFromCart, fetchCart, cartId } =
      useContext(CartContext);
   const navigation = useNavigation();

   useEffect(() => {
      if (cartId) {
         fetchCart(cartId);
      }
   }, [cartId]);

   const handleCheckOut = () => {
      navigation.navigate('checkout', { total: totalAmount });
   };

   const renderCartItem = ({ item }) => {
      const imageUri =
         item.product && item.product.images && item.product.images.length > 0
            ? `http://172.20.10.3:8080/api/v1/images/image/download/${item.product.images[0].imageId}`
            : null;

      return (
         <View style={styles.cartItem}>
            <Image source={imageUri ? { uri: imageUri } : placeholderImage} style={styles.image} />
            <View style={styles.itemDetails}>
               <Text style={styles.itemName}>{item.product?.name}</Text>
               <Text style={styles.itemPrice}>${item.unitPrice.toFixed(2)}</Text>
               <View style={styles.quantityContainer}>
                  <TouchableOpacity
                     onPress={() => updateCartItem(item.itemId, item.quantity - 1)}
                     style={styles.quantityButton}>
                     <Text style={styles.quantityText}>-</Text>
                  </TouchableOpacity>
                  <Text style={styles.quantity}>{item.quantity}</Text>
                  <TouchableOpacity
                     onPress={() => updateCartItem(item.itemId, item.quantity + 1)}
                     style={styles.quantityButton}>
                     <Text style={styles.quantityText}>+</Text>
                  </TouchableOpacity>
               </View>
            </View>
            <TouchableOpacity
               onPress={() => removeFromCart(item.itemId)}
               style={styles.removeButton}>
               <Ionicons name="trash-outline" size={20} color="purple" />
            </TouchableOpacity>
         </View>
      );
   };

   return (
      <View style={styles.container}>
         <Text style={styles.cartText}>My Cart</Text>
         {cart.length === 0 ? (
            <Text style={styles.emptyCartText}>Your cart is empty</Text>
         ) : (
            <>
               <FlatList
                  data={cart}
                  renderItem={renderCartItem}
                  keyExtractor={(item) => item.itemId.toString()}
               />
               <View style={styles.totalContainer}>
                  <Text style={styles.totalLabel}>Total: ${totalAmount.toFixed(2)}</Text>
               </View>
               <TouchableOpacity style={styles.checkoutButton} onPress={handleCheckOut}>
                  <Text style={styles.checkoutText}>Checkout</Text>
               </TouchableOpacity>
            </>
         )}
      </View>
   );
};

export default CartScreen;

const styles = StyleSheet.create({
   container: {
      flex: 1,
      padding: 10,
      backgroundColor: 'white',
   },
   cartText: {
      fontSize: 25,
      fontWeight: 'bold',
      color: 'purple',
      textAlign: 'center',
      marginTop: 15,
   },
   emptyCartText: {
      fontSize: 18,
      textAlign: 'center',
      marginTop: 70,
   },
   cartItem: {
      flexDirection: 'row',
      alignItems: 'center',
      padding: 10,
      borderBottomWidth: 1,
      borderColor: '#ddd',
   },
   image: {
      width: 70,
      height: 70,
      borderRadius: 5,
   },
   itemDetails: {
      flex: 1,
      marginLeft: 10,
   },
   itemName: {
      fontSize: 16,
      fontWeight: 'bold',
      marginBottom: 5,
   },
   itemPrice: {
      fontSize: 16,
      marginTop: 10,
      color: '#555',
   },
   quantityContainer: {
      flexDirection: 'row',
      alignItems: 'center',
   },
   quantityButton: {
      padding: 10,
   },
   quantityText: {
      fontSize: 20,
   },
   quantity: {
      fontSize: 16,
   },
   totalContainer: {
      marginTop: 20,
      alignItems: 'center',
   },
   totalLabel: {
      fontSize: 18,
      fontWeight: 'bold',
      marginBottom: 10,
   },
   checkoutButton: {
      backgroundColor: 'purple',
      padding: 15,
      borderRadius: 5,
      alignItems: 'center',
   },
   checkoutText: {
      fontSize: 18,
      color: '#fff',
   },
});
