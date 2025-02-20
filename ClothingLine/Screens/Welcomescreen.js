import { StyleSheet, Text, View, Image, TouchableOpacity } from 'react-native';
import React from 'react';
import { useNavigation } from '@react-navigation/native';

const Welcomescreen = () => {
   const navigation = useNavigation();

   const loginHandler = () => {
      navigation.navigate('Log in');
   };
   const SignupHandler = () => {
      navigation.navigate('Sign up');
   };
   return (
      <View style={styles.container}>
         <Image
            source={require('../assets/images/girl-removebg-preview.png')}
            style={styles.logo}
         />
         <Text style={styles.title}>Welcome To The Kadirian's Haven</Text>
         <Text style={styles.subTitle}>...where style meet you</Text>
         <View style={styles.buttonContainer}>
            <TouchableOpacity style={styles.loginButton} onPress={loginHandler}>
               <Text style={styles.loginText}>Login</Text>
            </TouchableOpacity>
            <TouchableOpacity style={styles.signupButton} onPress={SignupHandler}>
               <Text style={styles.signupText}>Sign Up</Text>
            </TouchableOpacity>
         </View>
      </View>
   );
};

export default Welcomescreen;

const styles = StyleSheet.create({
   container: {
      backgroundColor: 'white',
      flex: 1,
      alignItems: 'center',
   },
   logo: {
      height: 200,
      width: 150,
      marginVertical: 120,
      marginHorizontal: 120,
      marginBottom: 3,
   },
   title: {
      fontSize: 40,
      fontWeight: 'semibold',
      fontFamily: 'Cochin-BoldItalic',
      textAlign: 'center',
      color: 'purple',
   },
   subTitle: {
      marginTop: 20,
      marginLeft: 80,
      color: 'grey',
   },
   buttonContainer: {
      marginTop: 130,
      flexDirection: 'column',
      width: '70%',
   },
   loginButton: {
      borderWidth: 1,
      borderColor: 'purple',
      width: '90%',
      height: 50,
      borderRadius: 100,
      alignItems: 'center',
      justifyContent: 'center',
      marginBottom: 10,
      backgroundColor: 'purple',
      padding: 10,
   },
   signupButton: {
      borderWidth: 1,
      borderColor: 'purple',
      width: '90%',
      height: 50,
      borderRadius: 100,
      alignItems: 'center',
      justifyContent: 'center',
      backgroundColor: 'purple',
      padding: 10,
   },
   loginText: {
      color: 'white',
      fontFamily: 'Avenir-Light',
      fontSize: 25,
      fontWeight: 'bold',
   },
   signupText: {
      color: 'white',
      fontFamily: 'Avenir-Light',
      fontSize: 25,
      fontWeight: 'bold',
   },
});
