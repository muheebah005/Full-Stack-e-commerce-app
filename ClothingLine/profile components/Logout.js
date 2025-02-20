import AsyncStorage from '@react-native-async-storage/async-storage';
import { useNavigation } from '@react-navigation/native';
import React, { useContext } from 'react';
import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { AuthContext } from '../ItemCard/AuthContext';

const Logout = () => {
   const navigation = useNavigation();
   const { signOut } = useContext(AuthContext);

   const logout = async () => {
      try {
         await AsyncStorage.removeItem('userRole');
         await AsyncStorage.removeItem('userToken');
         signOut();
         navigation.getParent()?.reset({
            index: 0,
            routes: [{ name: 'Log in' }],
         });
      } catch (error) {
         console.error('Logout error:', error);
      }
   };

   return (
      <View style={styles.container}>
         <Text style={styles.title}>Logout function</Text>
         <TouchableOpacity onPress={logout} style={styles.button}>
            <Text style={styles.buttonText}>Log Out</Text>
         </TouchableOpacity>
      </View>
   );
};

export default Logout;

const styles = StyleSheet.create({
   container: {
      flex: 1,
      justifyContent: 'center',
      alignItems: 'center',
      backgroundColor: '#fff',
   },
   title: {
      fontSize: 24,
      marginBottom: 20,
   },
   button: {
      backgroundColor: 'purple',
      paddingVertical: 10,
      paddingHorizontal: 20,
      borderRadius: 5,
   },
   buttonText: {
      color: '#fff',
      fontSize: 18,
   },
});
