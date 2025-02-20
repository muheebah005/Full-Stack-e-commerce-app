import { StyleSheet, Text, View, Image } from 'react-native';
import React from 'react';

const CardComponent = () => {
   return (
      <View style={styles.card}>
         <Text style={styles.title}>"Discover the new season's best"</Text>
         <Text style={styles.subTitle}>"Update your wardrobe now!"</Text>
         <Image style={styles.image} source={require('../assets/images/woman-6466907_1280.jpg')} />
      </View>
   );
};

export default CardComponent;

const styles = StyleSheet.create({
   card: {
      borderRadius: 10,
      backgroundColor: 'lavender',
      padding: 20,
      margin: 10,
      alignItems: 'center',
      shadowColor: '#000',
      shadowOffset: { width: 0, height: 2 },
      shadowOpacity: 0.2,
      shadowRadius: 2,
      elevation: 3,
      marginTop: 30,
   },
   title: {
      fontSize: 18,
      fontWeight: 'bold',
      color: '#000',
   },
   subTitle: {
      fontSize: 14,
      color: '#666',
      marginVertical: 10,
   },
   image: {
      width: 100,
      height: 100,
      borderRadius: 50,
   },
});
