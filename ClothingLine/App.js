import { NavigationContainer } from '@react-navigation/native';
import React from 'react';
import { AuthContext, AuthProvider } from './ItemCard/AuthContext';
import { CartProvider } from './ItemCard/CartContext';
import AdminStackNavigator from './Navigation/AdminStackNavigator';
import AuthStack from './Navigation/AuthStack';
import StackNavigator from './Navigation/StackNavigator';

const AppNavigator = () => {
   const { authState } = React.useContext(AuthContext);

   return (
      <NavigationContainer key={authState.userToken ? authState.userToken : 'auth'}>
         {authState.userToken ? (
            authState.userRole === 'admin' ? (
               <AdminStackNavigator />
            ) : (
               <StackNavigator />
            )
         ) : (
            <AuthStack />
         )}
      </NavigationContainer>
   );
};

const App = () => {
   return (
      <CartProvider>
         <AuthProvider>
            <AppNavigator />
         </AuthProvider>
      </CartProvider>
   );
};

export default App;
