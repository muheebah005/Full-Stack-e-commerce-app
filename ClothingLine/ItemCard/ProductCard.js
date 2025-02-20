import Ionicons from '@expo/vector-icons/Ionicons';
import { useNavigation } from '@react-navigation/native';
import React, { useContext, useEffect, useState } from 'react';
import { Image, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import placeholderImage from '../assets/images/placeholderr.png';
import { CartContext } from './CartContext';

const ProductCard = ({ product }) => {
   const navigation = useNavigation();
   const { addToCart, removeFromCart, cart } = useContext(CartContext);

   const cartItem = cart.find((item) => item.product.id === product.id);
   const isInCart = Boolean(cartItem);

   const [isLiked, setIsLiked] = useState(isInCart);

   useEffect(() => {
      setIsLiked(isInCart);
   }, [isInCart]);

   const handleToggleCart = () => {
      if (isLiked) {
         if (cartItem?.itemId) {
            removeFromCart(cartItem.itemId);
         } else {
            console.warn('Cart item ID is undefined. Cannot remove.');
         }
      } else {
         addToCart(product);
      }
      setIsLiked(!isLiked);
   };

   const imageUri =
      product.images && product.images.length > 0
         ? `http://172.20.10.3:8080/api/v1/images/image/download/${product.images[0].imageId}`
         : null;

   return (
      <View style={styles.container}>
         <TouchableOpacity onPress={() => navigation.navigate('ProductDetailsOne', { product })}>
            <Image source={imageUri ? { uri: imageUri } : placeholderImage} style={styles.image1} />
            <Text style={styles.gownName}>{product.brand}</Text>
            <Text style={styles.gownName}>{product.name}</Text>
            <Text style={styles.price}>${product.price}</Text>
         </TouchableOpacity>
         <TouchableOpacity onPress={handleToggleCart} style={styles.iconContainer}>
            {isLiked ? (
               <Ionicons name="bag" size={20} color="purple" />
            ) : (
               <Ionicons name="bag-outline" size={20} color="purple" />
            )}
         </TouchableOpacity>
      </View>
   );
};

export default ProductCard;

const styles = StyleSheet.create({
   container: {
      flex: 1,
      backgroundColor: 'white',
   },
   image1: {
      height: 256,
      borderRadius: 20,
      width: 170,
      marginVertical: 10,
      marginLeft: 20,
      backgroundColor: 'lavender',
   },
   gownName: {
      fontWeight: 'bold',
      marginLeft: 12,
      fontSize: 20,
      fontFamily: 'Avenir-Light',
   },
   price: {
      marginLeft: 14,
      fontSize: 17,
   },
   iconContainer: {
      height: 34,
      width: 34,
      backgroundColor: 'white',
      justifyContent: 'center',
      alignItems: 'center',
      borderRadius: 17,
      position: 'absolute',
      top: 15,
      right: 25,
   },
});
