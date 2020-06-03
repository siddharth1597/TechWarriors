package sidsim.techwarriors;

public class RegistrationDetails {
        String name;
        String email;
        String password;
        public RegistrationDetails(){
            //For Reading
        }
        public RegistrationDetails( String name, String email,String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

}
