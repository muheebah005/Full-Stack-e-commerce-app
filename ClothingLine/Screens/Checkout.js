import {
   StyleSheet,
   Text,
   View,
   TextInput,
   Alert,
   TouchableOpacity,
   TouchableWithoutFeedback,
   Keyboard,
} from 'react-native';
import React from 'react';
import { useState } from 'react';

const Checkout = ({ navigation, route }) => {
   const { total } = route.params;

   const [cardNumber, setCardNumber] = useState('');
   const [expiryDate, setExpiryDate] = useState('');
   const [cvv, setCvv] = useState('');
   const [shippingAddress, setShippingAddress] = useState('');

   const handlePayment = () => {
      Alert.alert('Payment Success', 'Your Order has been confirmed');
      navigation.navigate('home');
   };

   return (
      <TouchableWithoutFeedback
         onPress={() => {
            Keyboard.dismiss();
         }}>
         <View style={styles.container}>
            <Text style={styles.header}>Checkout</Text>
            <TextInput
               style={styles.input}
               placeholder="card number"
               keyboardType="numeric"
               value={cardNumber}
               onChangeText={setCardNumber}
            />

            <TextInput
               style={styles.input}
               placeholder="Expiry Date (MM/YY)"
               keyboardType="numeric"
               value={expiryDate}
               onChangeText={setExpiryDate}
            />

            <TextInput
               style={styles.input}
               placeholder="CVV"
               keyboardType="numeric"
               value={cvv}
               onChangeText={setCvv}
            />
            <TextInput
               style={styles.input}
               placeholder="Shipping Address"
               value={shippingAddress}
               onChangeText={setShippingAddress}
            />
            <Text style={styles.totalText}>Total: ${total.toFixed(2)}</Text>

            <TouchableOpacity style={styles.paymentButton} onPress={handlePayment}>
               <Text style={styles.paymentButtonText}>Confirm Payment</Text>
            </TouchableOpacity>
         </View>
      </TouchableWithoutFeedback>
   );
};

export default Checkout;

const styles = StyleSheet.create({
   container: {
      flex: 1,
      padding: 20,
      backgroundColor: '#fff',
   },
   header: {
      fontSize: 24,
      fontWeight: 'bold',
      marginBottom: 20,
      marginLeft: 120,
      marginTop: 10,
   },
   input: {
      height: 50,
      borderColor: '#ccc',
      borderWidth: 1,
      borderRadius: 5,
      paddingHorizontal: 10,
      marginBottom: 30,
   },
   paymentButton: {
      backgroundColor: 'purple',
      padding: 15,
      borderRadius: 5,
      alignItems: 'center',
      marginTop: 230,
   },
   paymentButtonText: {
      color: '#fff',
      fontSize: 20,
   },
   totalText: {
      fontSize: 20,
      fontWeight: 'bold',
      marginBottom: 20,
   },
});
