import {
   StyleSheet,
   Text,
   View,
   TextInput,
   TouchableOpacity,
   TouchableWithoutFeedback,
   Keyboard,
   Alert,
} from 'react-native';
import React from 'react';
import Entypo from '@expo/vector-icons/Entypo';
import { useNavigation } from '@react-navigation/native';

const EditProfile = () => {
   const navigation = useNavigation();

   const handleGoBack = () => {
      navigation.navigate('profile');
   };

   const handleSaved = () => {
      Alert.alert('Your Profile have been updated');
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
                  <Entypo name="chevron-left" size={30} color={'purple'} style={styles.icon} />
               </TouchableOpacity>
               <Text style={styles.headerText}>Edit Profile</Text>
            </View>
            <View style={styles.inputContainer}>
               <View style={styles.inputs}>
                  <TextInput style={styles.textInput} placeholder="Username" />
               </View>
               <View style={styles.inputs}>
                  <TextInput style={styles.textInput} placeholder="email" autoCapitalize="none" />
               </View>
               <View style={styles.inputs}>
                  <TextInput style={styles.textInput} placeholder="phone" keyboardType="numeric" />
               </View>
            </View>
            <TouchableOpacity style={styles.saveContainer} onPress={handleSaved}>
               <Text style={styles.saveText}>Save</Text>
            </TouchableOpacity>
         </View>
      </TouchableWithoutFeedback>
   );
};

export default EditProfile;

const styles = StyleSheet.create({
   container: {
      flex: 1,
      backgroundColor: '#fff',
      padding: 20,
   },
   header: {
      flexDirection: 'row',
      paddingTop: 20,
   },
   headerText: {
      paddingLeft: 118,
      fontSize: 24,
   },
   inputContainer: {
      paddingTop: 40,
   },
   textInput: {
      fontSize: 20,
      padding: 5,
      paddingBottom: 4,
   },
   inputs: {
      // fontSize: 20,
      paddingTop: 20,
      borderWidth: 1,
      marginBottom: 20,
      padding: 10,
      borderColor: 'grey',
      borderRadius: 20,
      marginTop: 30,
   },
   saveContainer: {
      paddingLeft: 12,
      paddingTop: 20,
      marginTop: 20,
      backgroundColor: 'purple',
      padding: 15,
      borderRadius: 10,
      alignItems: 'center',
   },
   saveText: {
      fontSize: 20,
      color: '#fff',
   },
});
