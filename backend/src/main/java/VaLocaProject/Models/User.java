    package VaLocaProject.Models;

    import jakarta.persistence.Column;
    import jakarta.persistence.Entity;
    import jakarta.persistence.Table;

    @Entity // To define it as entity to springboot
    @Table(name = "users") // To define which table it represents
    public class User extends Account{

        @Column(name = "first_name", nullable=true)
        private String firstName = "";
        @Column(name = "last_name", nullable=true)
        private String lastName = "";

        // Explicit no-args constructor
        protected User() {
        }

        public User(Long id) {
            super(id);
        }
        public User(String email, String password) {
            super(email, password);
        }
    
        public String getFirstName(){
            return firstName;
        }

        public void setFirstName(String firstName){
            this.firstName = firstName;
        }

        public String getLastName(){
            return lastName;
        }

        public void setLastName(String lastName){
            this.lastName = lastName;
        }
    }