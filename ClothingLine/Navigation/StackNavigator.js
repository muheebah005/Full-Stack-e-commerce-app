import { createNativeStackNavigator } from '@react-navigation/native-stack';
import React from 'react';
import ProductDetailOne from '../ProductDetails/ProductDetailOne';
import Checkout from '../Screens/Checkout';
import ChangePassword from '../profile components/ChangePassword';
import EditProfile from '../profile components/EditProfile';
import Logout from '../profile components/Logout';
import BottomTabNavigator from './BottomTabNavigator';

const Stack = createNativeStackNavigator();

const StackNavigator = () => {
   return (
      <Stack.Navigator
         initialRouteName="Bottom"
         screenOptions={{
            headerShown: false,
         }}>
         <Stack.Screen name="Bottom" component={BottomTabNavigator} />
         <Stack.Screen name="ProductDetailsOne" component={ProductDetailOne} />
         <Stack.Screen name="checkout" component={Checkout} />
         <Stack.Screen name="changePassword" component={ChangePassword} />
         <Stack.Screen name="editProfile" component={EditProfile} />
         <Stack.Screen name="logOut" component={Logout} />
      </Stack.Navigator>
   );
};

export default StackNavigator;
