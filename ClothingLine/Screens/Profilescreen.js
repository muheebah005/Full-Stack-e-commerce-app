import Entypo from '@expo/vector-icons/Entypo';
import Feather from '@expo/vector-icons/Feather';
import Ionicons from '@expo/vector-icons/Ionicons';
import MaterialCommunityIcons from '@expo/vector-icons/MaterialCommunityIcons';
import { useNavigation } from '@react-navigation/native';
import React from 'react';
import { Image, StyleSheet, Text, TouchableOpacity, View } from 'react-native';

const Profilescreen = () => {
   const navigation = useNavigation();

   const handleEditProfile = () => {
      navigation.navigate('editProfile');
   };

   const handlePasswordReset = () => {
      navigation.navigate('changePassword');
   };

   const handleLogOut = () => {
      navigation.navigate('logOut');
   };

   return (
      <View style={styles.container}>
         <View style={styles.profileTextContainer}>
            <Text style={styles.profileText}>My Profile</Text>
         </View>
         <View style={styles.profileContainer}>
            <Image
               source={require('../assets/images/woman-6466907_1280.jpg')}
               style={styles.profilePicture}
            />
            <Text style={styles.name}>John Doe</Text>
            <Text style={styles.telephone}>+1 584 548 5463</Text>
            <View style={styles.profileAction}>
               <TouchableOpacity style={styles.profileActionButton} onPress={handleEditProfile}>
                  <Feather name="edit-3" size={20} color={'purple'} style={styles.editIcon} />
                  <Text style={styles.profileActionText}>Edit Profile</Text>
                  <Entypo
                     name="chevron-small-right"
                     size={24}
                     color={'purple'}
                     style={styles.rightIcon}
                  />
               </TouchableOpacity>
               <TouchableOpacity style={styles.profileActionButton} onPress={handlePasswordReset}>
                  <MaterialCommunityIcons
                     name="lock-outline"
                     size={20}
                     color={'purple'}
                     style={styles.editIcon}
                  />
                  <Text style={styles.profileActionText}>Change Password</Text>
                  <Entypo
                     name="chevron-small-right"
                     size={24}
                     color={'purple'}
                     style={styles.rightIconPassword}
                  />
               </TouchableOpacity>
               <TouchableOpacity style={styles.profileActionButton} onPress={handleLogOut}>
                  <Ionicons
                     name="log-out-outline"
                     size={22}
                     color={'purple'}
                     style={styles.editIcon}
                  />
                  <Text style={styles.profileActionText}>Log Out</Text>
                  <Entypo
                     name="chevron-small-right"
                     size={24}
                     color={'purple'}
                     style={styles.rightIcon}
                  />
               </TouchableOpacity>
            </View>
         </View>
      </View>
   );
};

export default Profilescreen;

const styles = StyleSheet.create({
   container: {
      flex: 1,
      padding: 20,
      backgroundColor: '#fff',
   },
   profileTextContainer: {
      paddingTop: 10,
      // marginVertical: 20,
      marginHorizontal: 145,
   },
   profileText: {
      fontSize: 21,
      color: 'purple',
   },
   profileContainer: {
      // alignItems: 'center',
      marginBottom: 30,
   },
   profilePicture: {
      width: 100,
      height: 100,
      borderRadius: 50,
      marginTop: 30,
   },
   name: {
      fontSize: 25,
      paddingTop: 15,
      marginLeft: 10,
   },
   telephone: {
      color: 'grey',
      paddingTop: 3,
      marginLeft: 10,
   },
   profileAction: {
      paddingHorizontal: 16,
      paddingTop: 40,
   },
   profileActionButton: {
      backgroundColor: 'lavender',
      paddingVertical: 17,
      paddingHorizontal: 12,
      borderRadius: 8,
      marginBottom: 30,
      borderColor: '#ddd',
      borderWidth: 1,
      flexDirection: 'row',
   },
   profileActionText: {
      fontSize: 16,
      paddingRight: 12,
      paddingLeft: 12,
   },
   editIcon: {
      paddingLeft: 10,
   },
   rightIcon: {
      paddingLeft: 180,
   },
   rightIconPassword: {
      paddingLeft: 128,
   },
});
