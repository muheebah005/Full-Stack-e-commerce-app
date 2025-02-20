import { StyleSheet, Text, View } from 'react-native';
import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import AdminDashboard from '../AdminScreen/AdminDashboard';

const Stack = createNativeStackNavigator();

const AdminStackNavigator = () => {
   return (
      <Stack.Navigator screenOptions={{ headerShown: false }}>
         <Stack.Screen name="AdminDashboard" component={AdminDashboard} />
      </Stack.Navigator>
   );
};

export default AdminStackNavigator;

const styles = StyleSheet.create({});
