import { StyleSheet, Text, View } from 'react-native';
import React from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import Productlistingscreen from '../Screens/Productlistingscreen';
import Cartscreen from '../Screens/Cartscreen';
import Profilescreen from '../Screens/Profilescreen';
import Ionicons from '@expo/vector-icons/Ionicons';

const Tab = createBottomTabNavigator();

const BottomTabNavigator = () => {
   return (
      <Tab.Navigator
         screenOptions={{
            headerShown: false,
            tabBarShowLabel: false,
            tabBarActiveTintColor: 'purple',
            tabBarInactiveTintColor: 'grey',
         }}>
         <Tab.Screen
            name="home"
            component={Productlistingscreen}
            options={{
               tabBarIcon: ({ size, color }) => <Ionicons name="home" size={size} color={color} />,
            }}
         />
         <Tab.Screen
            name="my cart"
            component={Cartscreen}
            options={{
               tabBarIcon: ({ size, color }) => <Ionicons name="bag" size={size} color={color} />,
            }}
         />
         <Tab.Screen
            name="profile"
            component={Profilescreen}
            options={{
               tabBarIcon: ({ size, color }) => (
                  <Ionicons name="person" size={size} color={color} />
               ),
            }}
         />
      </Tab.Navigator>
   );
};

export default BottomTabNavigator;

const styles = StyleSheet.create({});
