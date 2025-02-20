import {
   StyleSheet,
   Text,
   View,
   TouchableOpacity,
   TextInput,
   TouchableWithoutFeedback,
   Keyboard,
   Alert,
} from 'react-native';
import React from 'react';
import Entypo from '@expo/vector-icons/Entypo';
import { useNavigation } from '@react-navigation/native';

const ChangePassword = () => {
   const navigation = useNavigation();

   const handleGoBack = () => {
      navigation.navigate('profile');
   };

   const handleUpdated = () => {
      Alert.alert('Your Password have been successfully updated');
      navigation.navigate('profile');
   };
   return (
      <TouchableWithoutFeedback
         onPress={() => {
            Keyboard.dismiss();
         }}>
         <View style={styles.container}>
            <View style={styles.header}>
               <TouchableOpacity onPress={handleGoBack}>
                  <Entypo name="chevron-left" size={25} color={'purple'} style={styles.icon} />
               </TouchableOpacity>
               <Text style={styles.headerText}>Reset Password</Text>
            </View>
            <View style={styles.inputs}>
               <TextInput style={styles.textInput} placeholder=" Current Password" />
            </View>
            <View style={styles.inputs}>
               <TextInput style={styles.textInput} placeholder="New Password" />
            </View>
            <View style={styles.inputs}>
               <TextInput style={styles.textInput} placeholder="Confirm New Password" />
            </View>
            <TouchableOpacity style={styles.updateContainer} onPress={handleUpdated}>
               <Text style={styles.updateText}>Update Password</Text>
            </TouchableOpacity>
         </View>
      </TouchableWithoutFeedback>
   );
};

export default ChangePassword;

const styles = StyleSheet.create({
   container: {
      padding: 20,
      flex: 1,
      backgroundColor: '#fff',
   },
   header: {
      marginVertical: 20,
   },
   icon: {
      paddingBottom: 35,
   },
   headerText: {
      fontSize: 25,
      paddingBottom: 10,
   },
   textInput: {
      fontSize: 20,
      padding: 5,
      paddingBottom: 2,
   },
   inputs: {
      // fontSize: 20,
      paddingTop: 20,
      borderWidth: 1,
      marginBottom: 30,
      padding: 10,
      borderColor: 'grey',
      borderRadius: 20,
      marginTop: 10,
   },
   updateContainer: {
      paddingLeft: 12,
      paddingTop: 20,
      marginTop: 20,
      backgroundColor: 'purple',
      padding: 15,
      borderRadius: 10,
      alignItems: 'center',
   },
   updateText: {
      fontSize: 20,
      color: '#fff',
   },
});
