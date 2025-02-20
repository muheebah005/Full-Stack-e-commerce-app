import Ionicons from '@expo/vector-icons/Ionicons';
import { useNavigation } from '@react-navigation/native';
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import {
   ActivityIndicator,
   FlatList,
   Keyboard,
   StyleSheet,
   Text,
   TouchableOpacity,
   TouchableWithoutFeedback,
   View,
} from 'react-native';
import ProductCard from '../ItemCard/ProductCard';
import CardComponent from './CardComponent';

const Productlistingscreen = () => {
   const navigation = useNavigation();
   const [products, setProducts] = useState([]);
   const [loading, setLoading] = useState(true);

   useEffect(() => {
      const fetchProducts = async () => {
         try {
            const response = await axios.get(
               'http://172.20.10.3:8080/api/v1/products/all/products'
            );
            setProducts(response.data.data);
         } catch (error) {
            console.error('Error fetching products:', error);
         } finally {
            setLoading(false);
         }
      };
      fetchProducts();
   }, []);

   const handleProductPress = (product) => {
      navigation.navigate('ProductDetailsOne', { product });
   };
   if (loading) {
      return (
         <View style={styles.loaderContainer}>
            <ActivityIndicator size="large" color="purple" />
         </View>
      );
   }

   return (
      <TouchableWithoutFeedback onPress={Keyboard.dismiss}>
         <View style={styles.container}>
            <View style={styles.secondContainer}>
               <TouchableOpacity onPress={() => navigation.navigate('my cart')}>
                  <Ionicons name="bag" size={26} color={'purple'} />
               </TouchableOpacity>
            </View>
            <Text style={styles.headerText}>"Find Your Style Match!"</Text>
            {/* <CardComponent /> */}
            <FlatList
               data={products}
               keyExtractor={(item) => item.id.toString()}
               renderItem={({ item }) => (
                  <ProductCard product={item} onPress={() => handleProductPress(item)} />
               )}
               numColumns={2}
               contentContainerStyle={styles.productList}
               ListHeaderComponent={<CardComponent />}
            />
         </View>
      </TouchableWithoutFeedback>
   );
};

export default Productlistingscreen;

const styles = StyleSheet.create({
   container: {
      backgroundColor: 'white',
      flex: 2,
   },
   secondContainer: {
      marginTop: 10,
      justifyContent: 'space-between',
      alignItems: 'center',
      padding: 15,
      paddingLeft: 345,
   },
   headerText: {
      fontSize: 35,
      paddingTop: 20,
      marginLeft: 10,
      fontFamily: 'Avenir-Light',
      fontWeight: 'bold',
      color: 'purple',
   },
   productList: {
      paddingHorizontal: 10,
      paddingBottom: 20,
      padding: 5,
   },
   loaderContainer: {
      flex: 1,
      justifyContent: 'center',
      alignItems: 'center',
   },
});
