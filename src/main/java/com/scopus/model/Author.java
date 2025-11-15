package com.scopus.model;


    public class Author {
        private String authorId;
        private String firstName;
        private String lastName;
        private String affiliation;

        public Author() {
        }

        public Author(String authorId, String firstName, String lastName) {
            this.authorId = authorId;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        // Getters et Setters
        public String getAuthorId() {
            return authorId;
        }

        public void setAuthorId(String authorId) {
            this.authorId = authorId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getAffiliation() {
            return affiliation;
        }

        public void setAffiliation(String affiliation) {
            this.affiliation = affiliation;
        }

        public String getFullName() {
            return firstName + " " + lastName;
        }

        @Override
        public String toString() {
            return "Author{" +
                    "authorId='" + authorId + '\'' +
                    ", name='" + getFullName() + '\'' +
                    ", affiliation='" + affiliation + '\'' +
                    '}';
        }
    }

