import { createNativeStackNavigator } from '@react-navigation/native-stack';
import React from 'react';
import { StyleSheet } from 'react-native';
import Loginscreen from '../Screens/Loginscreen';
import Signupscreen from '../Screens/Signupscreen';
import Welcomescreen from '../Screens/Welcomescreen';

const Stack = createNativeStackNavigator();

const AuthStack = () => {
   return (
      <Stack.Navigator
         screenOptions={{
            headerShown: false,
         }}>
         <Stack.Screen name="Welcome" component={Welcomescreen} />

         <Stack.Screen
            name="Log in"
            component={Loginscreen}
            options={{
               gestureEnabled: false,
            }}
         />

         <Stack.Screen
            name="Sign up"
            component={Signupscreen}
            options={{
               gestureEnabled: false,
            }}
         />
      </Stack.Navigator>
   );
};

export default AuthStack;

const styles = StyleSheet.create({});
