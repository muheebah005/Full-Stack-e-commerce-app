import AsyncStorage from '@react-native-async-storage/async-storage';
import React, { createContext, useEffect, useState } from 'react';
import { ActivityIndicator, View } from 'react-native';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
   const [isLoading, setIsLoading] = useState(true);
   const [authState, setAuthState] = useState({
      userToken: null,
      userRole: null,
   });

   const signIn = (token, role) => {
      setAuthState({ userToken: token, userRole: role });
   };

   const signOut = () => {
      setAuthState({ userToken: null, userRole: null });
   };

   useEffect(() => {
      const bootstrapAsync = async () => {
         try {
            const token = await AsyncStorage.getItem('userToken');
            const role = await AsyncStorage.getItem('userRole');
            if (token) {
               setAuthState({ userToken: token, userRole: role });
            }
         } catch (e) {
            console.error('Error bootstrapping auth:', e);
         } finally {
            setIsLoading(false);
         }
      };
      bootstrapAsync();
   }, []);

   if (isLoading) {
      return (
         <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
            <ActivityIndicator size="large" />
         </View>
      );
   }

   return (
      <AuthContext.Provider value={{ authState, signIn, signOut }}>{children}</AuthContext.Provider>
   );
};
