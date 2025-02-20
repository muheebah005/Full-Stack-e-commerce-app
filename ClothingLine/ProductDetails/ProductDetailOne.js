import AntDesign from '@expo/vector-icons/AntDesign';
import { useNavigation, useRoute } from '@react-navigation/native';
import React, { useContext, useState } from 'react';
import { Image, ScrollView, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { CartContext } from '../ItemCard/CartContext';
import placeholderImage from '../assets/images/placeholderr.png';

const ProductDetailOne = () => {
   const navigation = useNavigation();
   const route = useRoute();
   const { product } = route.params;

   const handleGoBack = () => {
      navigation.goBack();
   };

   const { addToCart } = useContext(CartContext);

   const handleAddToCart = () => {
      addToCart(product);
   };

   const [selectedColor, setSelectedColor] = useState('White');
   const [selectedSize, setSelectedSize] = useState('S');

   const colors = ['White', 'Black', 'Blue'];
   const sizes = ['S', 'M', 'L'];

   const imageUri =
      product.images && product.images.length > 0
         ? `http://172.20.10.3:8080/api/v1/images/image/download/${product.images[0].imageId}`
         : null;

   return (
      <ScrollView style={styles.container}>
         <View style={styles.header}>
            <TouchableOpacity onPress={handleGoBack}>
               <AntDesign name="left" size={25} color={'purple'} style={styles.backArrow} />
            </TouchableOpacity>
         </View>
         <Image
            source={imageUri ? { uri: imageUri } : placeholderImage}
            style={styles.detailImage}
         />

         <View style={styles.detailContainer}>
            <Text style={styles.brand}>{product.brand}</Text>
            <Text style={styles.name}>{product.name}</Text>
            <Text style={styles.price}>${parseFloat(product.price).toFixed(2)}</Text>
            <Text style={styles.details}>{product.description}</Text>
            <Text style={styles.colorText}>Color</Text>
            <ScrollView horizontal>
               {colors.map((color, index) => (
                  <TouchableOpacity
                     key={index}
                     style={[
                        styles.optionButton,
                        selectedColor === color && styles.selectedOptionButton,
                     ]}
                     onPress={() => setSelectedColor(color)}>
                     <Text style={styles.colorButtonText}>{color}</Text>
                  </TouchableOpacity>
               ))}
            </ScrollView>
            <Text style={styles.colorText}>Size</Text>
            <ScrollView horizontal>
               {sizes.map((size, index) => (
                  <TouchableOpacity
                     key={index}
                     style={[
                        styles.optionButton,
                        selectedSize === size && styles.selectedOptionButton,
                     ]}
                     onPress={() => setSelectedSize(size)}>
                     <Text style={styles.colorButtonText}>{size}</Text>
                  </TouchableOpacity>
               ))}
            </ScrollView>
            <TouchableOpacity style={styles.addToCartButton} onPress={handleAddToCart}>
               <Text style={styles.addToCartText}>+ Add To Cart</Text>
            </TouchableOpacity>
         </View>
      </ScrollView>
   );
};

export default ProductDetailOne;

const styles = StyleSheet.create({
   container: {
      flex: 1,
      backgroundColor: 'lavender',
   },
   header: {
      padding: 20,
   },
   backArrow: {
      paddingTop: 20,
   },
   detailImage: {
      width: '90%',
      height: 280,
      resizeMode: 'contain',
   },
   detailContainer: {
      padding: 20,
      backgroundColor: '#b19cd9',
      borderTopLeftRadius: 20,
      borderTopRightRadius: 20,
      marginTop: -30,
   },
   brand: {
      fontSize: 16,
      color: 'purple',
      marginBottom: 10,
   },
   name: {
      fontSize: 25,
      fontWeight: 'bold',
      fontFamily: 'Avenir light',
   },
   price: {
      fontSize: 22,
      // fontWeight: 'bold',
      marginVertical: 10,
   },
   details: {
      fontSize: 16,
      color: 'black',
      marginVertical: 10,
   },
   colorText: {
      fontSize: 18,
      fontWeight: 'bold',
      marginVertical: 10,
   },
   optionButton: {
      borderWidth: 1,
      borderColor: 'black',
      borderRadius: 5,
      padding: 10,
      marginRight: 30,
   },
   selectedOptionButton: {
      borderColor: 'black',
      backgroundColor: '#d8bfd8',
   },
   colorButtonText: {
      fontWeight: '500',
      fontSize: '16',
   },
   addToCartButton: {
      backgroundColor: 'black',
      borderRadius: 10,
      marginTop: 20,
      padding: 20,
   },
   addToCartText: {
      color: 'white',
      marginLeft: 120,
   },
});
